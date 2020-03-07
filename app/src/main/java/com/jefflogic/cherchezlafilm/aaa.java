package com.jefflogic.cherchezlafilm;

import java.util.ArrayList;
import java.util.List;

public class aaa {

    private List<String> createItemList() {
        List<String> itemList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            itemList.add("Кот " + i);
        }
        return itemList;
    }

}
