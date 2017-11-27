package com.ayvengoza.activ.letsmove;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by ang on 27.11.17.
 */

public interface ActiveDao {

    @Insert
    void insert(Active active);

    @Delete
    void delete(Active active);

    @Query("SELECT * FROM active WHERE time ")
    List<Active> getAllActives();
}
