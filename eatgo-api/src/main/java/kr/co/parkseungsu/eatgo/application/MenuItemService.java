package kr.co.parkseungsu.eatgo.application;

import kr.co.parkseungsu.eatgo.domain.MenuItem;
import kr.co.parkseungsu.eatgo.domain.MenuItemRepository;
import kr.co.parkseungsu.eatgo.domain.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService {
    private MenuItemRepository menuItemRepository;

    @Autowired
    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository=menuItemRepository;
    }

    public void bulkUpdate(Long restaurantId, List<MenuItem> menuItems) {
        for(MenuItem menuItem:menuItems) {
            menuItem.setRestaurantId(restaurantId);
            menuItemRepository.save(menuItem);
        }
    }
}
