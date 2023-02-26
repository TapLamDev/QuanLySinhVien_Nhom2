/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QuanLyGiaoDuc.DAO;

import java.util.List;

/**
 *
 * @author hoang
 */
abstract public class QLGDDAO<Entity, KeyType> {

    abstract public void insert(Entity entity);

    abstract public void update(Entity entity);

    abstract public void delete(KeyType key);

    abstract public List<Entity> selectAll();

    abstract public Entity selectById(KeyType key);

    abstract protected List<Entity> selectBySQL(String sql, Object... args);
}
