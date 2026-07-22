package com.example.demo.repository;
import com.example.demo.entity.*; import org.springframework.stereotype.Repository; import java.io.*; import java.util.*; import java.util.concurrent.*; import java.util.concurrent.atomic.AtomicLong;
@Repository
public class InMemorySalesRepository {
    public final AtomicLong productSequence=new AtomicLong(4), orderSequence=new AtomicLong(1000);
    public final Map<String,User> users=new ConcurrentHashMap<>(); public final Map<Long,Product> products=new ConcurrentHashMap<>(); public final Set<String> categories=ConcurrentHashMap.newKeySet(); public final Map<String,List<CartItem>> carts=new ConcurrentHashMap<>(); public final Map<Long,Order> orders=new ConcurrentHashMap<>(); public final Map<String,String> tokens=new ConcurrentHashMap<>();

    private static final String DATA_FILE = "sales_data.dat";

    public InMemorySalesRepository(){
        loadData();
        Runtime.getRuntime().addShutdownHook(new Thread(this::saveData));
        Thread saverThread = new Thread(this::periodicallySave);
        saverThread.setDaemon(true);
        saverThread.start();
    }

    private void periodicallySave() {
        while (true) {
            try {
                Thread.sleep(2000);
                saveData();
            } catch (InterruptedException e) {
                break;
            } catch (Exception e) {
                System.err.println("Error running background save: " + e.getMessage());
            }
        }
    }

    private synchronized void saveData() {
        try {
            RepositoryState state = new RepositoryState();
            state.productSequence = productSequence.get();
            state.orderSequence = orderSequence.get();
            state.users = new HashMap<>(users);
            state.products = new HashMap<>(products);
            state.categories = new HashSet<>(categories);
            state.carts = new HashMap<>(carts);
            state.orders = new HashMap<>(orders);
            state.tokens = new HashMap<>(tokens);

            File file = new File(DATA_FILE);
            File tempFile = new File(DATA_FILE + ".tmp");
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(tempFile))) {
                oos.writeObject(state);
            }
            if (tempFile.renameTo(file)) {
                // Renamed successfully
            } else {
                if (file.exists() && !file.delete()) {
                    System.err.println("Failed to delete old data file");
                }
                if (!tempFile.renameTo(file)) {
                    System.err.println("Failed to rename temp file to data file");
                }
            }
        } catch (Throwable e) {
            System.err.println("Failed to save repository state: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadData() {
        File file = new File(DATA_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                RepositoryState state = (RepositoryState) ois.readObject();
                productSequence.set(state.productSequence);
                orderSequence.set(state.orderSequence);
                if (state.users != null) users.putAll(state.users);
                if (state.products != null) products.putAll(state.products);
                if (state.categories != null) categories.addAll(state.categories);
                if (state.carts != null) carts.putAll(state.carts);
                if (state.orders != null) orders.putAll(state.orders);
                if (state.tokens != null) tokens.putAll(state.tokens);
                System.out.println("Loaded database from " + file.getAbsolutePath());
                return;
            } catch (Throwable e) {
                System.err.println("Error loading data file, falling back to defaults: " + e.getMessage());
                e.printStackTrace();
            }
        }
        initDefaultData();
    }

    private void initDefaultData() {
        users.put("admin@sales.local",new User("Quản trị viên","admin@sales.local","0900000000","admin123","ADMIN",true)); users.put("customer@sales.local",new User("Nguyễn Minh Anh","customer@sales.local","0912345678","customer123","CUSTOMER",true));
        products.put(1L,new Product(1,"Laptop Nova Pro 14","Thiết bị",24990000,12,true,"Laptop làm việc hiệu năng cao","https://images.unsplash.com/photo-1496181133206-80ce9b88a853?auto=format&fit=crop&w=700&q=80")); products.put(2L,new Product(2,"Tai nghe AirBeat","Phụ kiện",1290000,40,true,"Tai nghe không dây chống ồn","https://images.unsplash.com/photo-1505740420928-5e560c06d30e?auto=format&fit=crop&w=700&q=80")); products.put(3L,new Product(3,"Bàn phím KeyFlow","Phụ kiện",890000,25,true,"Bàn phím cơ gọn nhẹ","https://images.unsplash.com/photo-1587829741301-dc798b83add3?auto=format&fit=crop&w=700&q=80"));
        categories.addAll(List.of("Thiết bị","Phụ kiện"));
        saveData();
    }

    private static class RepositoryState implements java.io.Serializable {
        private static final long serialVersionUID = 1L;
        long productSequence;
        long orderSequence;
        Map<String, User> users;
        Map<Long, Product> products;
        Set<String> categories;
        Map<String, List<CartItem>> carts;
        Map<Long, Order> orders;
        Map<String, String> tokens;
    }
}
