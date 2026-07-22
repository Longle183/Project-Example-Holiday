package com.example.demo.entity;

import java.util.Map;
public class User implements java.io.Serializable {
    public String name,email,phone,password,role; public boolean active;
    public User(String name,String email,String phone,String password,String role,boolean active){this.name=name;this.email=email;this.phone=phone;this.password=password;this.role=role;this.active=active;}
    public Map<String,Object> publicView(){return Map.of("name",name,"email",email,"phone",phone==null?"":phone,"role",role,"active",active);}
}
