package com.destroyordefend.project.Unit;

public interface Damage {
    int canShot = 0;

    void decrease();

    int DoDamage();

    void AcceptDamage(int damage);
}
