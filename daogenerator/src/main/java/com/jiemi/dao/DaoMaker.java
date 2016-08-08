package com.jiemi.dao;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class DaoMaker {
    private static int version=1;

      public  static  void main(String[] args){
          Schema schema=new Schema(version,"com.jiemi.entity");

      }
      private static  void addMenu(Schema schema){
          Entity entity=schema.addEntity("Menu");

      }

}
