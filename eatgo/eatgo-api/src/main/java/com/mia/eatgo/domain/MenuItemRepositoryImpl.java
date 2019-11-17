package com.mia.eatgo.domain;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MenuItemRepositoryImpl implements MenuItemRepository {

    List<MenuItem> menuItems = new ArrayList<>();

    public MenuItemRepositoryImpl() {
        menuItems.add(new MenuItem("kimchi"));
    }

    @Override
    public List<MenuItem> findAllByRestaurantId(Long restaurantId) {
        return menuItems;
    }
}
