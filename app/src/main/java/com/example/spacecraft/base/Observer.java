package com.example.spacecraft.base;

public abstract class Observer {
     public GameObject gameObject;
     public abstract void notify(GameObject gameObject,Object arg);
}
