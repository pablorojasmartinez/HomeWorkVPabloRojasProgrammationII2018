/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author Pablo Rojas Martínez
 */
public interface Buffer {

    public void set(int value);  // place value into Buffer

    public int get();              // return value from Buffer

}
