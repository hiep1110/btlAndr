package com.example.spacecraft.base;

import java.util.ArrayList;
import java.util.List;

public  class Subject {
    public List<Observer> observers = new ArrayList<>();
    public void attachObserver(Observer observer){
        observers.add(observer);
    }

    public void detachObserver(Observer observer){
        observers.remove(observer);
    }

    public void notifyObservers(GameObject gameObject,Object arg){
        observers.forEach(observer->{
            observer.notify(gameObject,arg);
        });
    }

}
