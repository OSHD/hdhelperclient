package com.hdhelper.api;

import com.hdhelper.Main;
import com.hdhelper.peer.RSItemContainer;
import com.hdhelper.peer.RSNodeTable;


public class ItemTable {

    public static final int INVENTORY   = 93;
    public static final int EQUIPMENT   = 94;
    public static final int BANK        = 95;

    public static RSItemContainer get(long id) {
        if(id < 0) return null;
        RSNodeTable table = Main.client.getItemContainers();
        return (RSItemContainer) table.get(id);
    }

    public static int getItemCount(int table, int item_id) {
        RSItemContainer tbl = get( table );
        if(tbl == null) return 0;
        int[] ids = tbl.getIds();
        int[] num = tbl.getQuantities();
        assert ids.length == num.length;
        int num_items = tbl.getIds().length;
        for(int i = 0; i < num_items; i++) {
            if(ids[i] == item_id) return num[i];
        }
        return 0;
    }

    public static Item getItemAt(int table, int index) {
        RSItemContainer tbl = get( table );
        if(tbl == null) return null;
        int[] ids = tbl.getIds();
        int[] nums = tbl.getQuantities();
        assert ids.length == nums.length;
        if(index >= ids.length) return null;
        int item_id = ids[index];
        int item_num = nums[index];
        if(item_id<0||item_num<=0) return null;
        return new Item(item_id,item_num);
    }

    public static int getItemIdAt(int table, int index) {
        if (index < 0) return -1;
        RSItemContainer tbl = get( table );
        if(tbl == null) return -1;
        int[] ids = tbl.getIds();
        if(index >= ids.length) return -1;
        return ids[index];
    }

    public static int indexOf(int table, int item) {
        if(item < 0) return -1;
        RSItemContainer tbl = get( table );
        if(tbl == null) return 0;
        int[] ids = tbl.getIds();
        for(int i = 0; i < ids.length; i++) {
            if(ids[i] == item) return i;
        }
        return -1;
    }




}
