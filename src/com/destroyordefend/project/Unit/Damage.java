package com.destroyordefend.project.Unit;

public interface Damage {
    void decrease();
    void doDamage();
    int getDamage();
    void acceptDamage(int damage);
}
