package com.ezgroceries.shoppinglist.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ezgroceries.shoppinglist.model.ShoppingListResource;
import com.ezgroceries.shoppinglist.services.ShoppingListService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author Mathias Vanhove (je25293)
 * @since release/yyyymm  (2019-05-13)
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ComponentScan("com.ezgroceries.shoppinglist.controller")
public class ShoppingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShoppingListService shoppingListService;

    @Test
    public void createShoppingList() throws Exception {
        given(shoppingListService.createShoppingList("Stephanie's birthday")).willReturn(getShoppingListMock());
        mockMvc
                .perform(
                        post("/shopping-lists")
                            .accept(MediaType.APPLICATION_JSON)
                            .content("{\"name\": \"Stephanie's birthday\"}")
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("shoppingListId").value("eb18bb7c-61f3-4c9f-981c-55b1b8ee8915"))
                .andExpect(jsonPath("name").value("Stephanie's birthday"));
        verify(shoppingListService).createShoppingList("Stephanie's birthday");
    }

    private ShoppingListResource getShoppingListMock() {
        return new ShoppingListResource(UUID.fromString("eb18bb7c-61f3-4c9f-981c-55b1b8ee8915"), "Stephanie's birthday");
    }

    @Test
    public void addCocktails() throws Exception {
        mockMvc
                .perform(
                        post("/shopping-lists/97c8e5bd-5353-426e-b57b-69eb2260ace3/cocktails")
                            .accept(MediaType.APPLICATION_JSON)
                            .content("[{\"cocktailId\": \"23b3d85a-3928-41c0-a533-6538a71e17c4\"}, {\"cocktailId\": \"d615ec78-fe93-467b-8d26-5d26d8eab073\"}]")
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("[0].cocktailId").value("23b3d85a-3928-41c0-a533-6538a71e17c4"));
    }

    @Test
    public void getShoppingList() throws Exception {
        given(shoppingListService.get("eb18bb7c-61f3-4c9f-981c-55b1b8ee8915")).willReturn(getShoppingListWithIngredientsMock());
        mockMvc
                .perform(
                        get("/shopping-lists/eb18bb7c-61f3-4c9f-981c-55b1b8ee8915")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.shoppingListId").value("eb18bb7c-61f3-4c9f-981c-55b1b8ee8915"))
                .andExpect(jsonPath("$.name").value("Stephanie's birthday"))
                .andExpect(jsonPath("$.ingredients.length()").value(5))
                .andExpect(jsonPath("$.ingredients[0]").value("Tequila"))
                .andExpect(jsonPath("$.ingredients[1]").value("Triple sec"))
                .andExpect(jsonPath("$.ingredients[2]").value("Lime juice"))
                .andExpect(jsonPath("$.ingredients[3]").value("Salt"))
                .andExpect(jsonPath("$.ingredients[4]").value("Blue Curacao"));
        verify(shoppingListService).get("eb18bb7c-61f3-4c9f-981c-55b1b8ee8915");
    }

    private ShoppingListResource getShoppingListWithIngredientsMock() {
        ShoppingListResource shoppingList = getShoppingListMock();
        Arrays.asList("Tequila",
                "Triple sec",
                "Lime juice",
                "Salt",
                "Blue Curacao").forEach(shoppingList::addIngredient);
        return shoppingList;
    }

    @Test
    public void getShoppingLists() throws Exception {
        given(shoppingListService.getAllShoppingLists()).willReturn(getShoppingListsMock());
        mockMvc
                .perform(
                        get("/shopping-lists")
                            .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].shoppingListId").value("4ba92a46-1d1b-4e52-8e38-13cd56c7224c"))
                .andExpect(jsonPath("$[0].name").value("Stephanie's birthday"))
                .andExpect(jsonPath("$[0].ingredients.length()").value(5))
                .andExpect(jsonPath("$[0].ingredients[0]").value("Tequila"))
                .andExpect(jsonPath("$[0].ingredients[1]").value("Triple sec"))
                .andExpect(jsonPath("$[0].ingredients[2]").value("Lime juice"))
                .andExpect(jsonPath("$[0].ingredients[3]").value("Salt"))
                .andExpect(jsonPath("$[0].ingredients[4]").value("Blue Curacao"))
                .andExpect(jsonPath("$[1].shoppingListId").value("6c7d09c2-8a25-4d54-a979-25ae779d2465"))
                .andExpect(jsonPath("$[1].name").value("My birthday"))
                .andExpect(jsonPath("$[1].ingredients.length()").value(5))
                .andExpect(jsonPath("$[1].ingredients[0]").value("Tequila"))
                .andExpect(jsonPath("$[1].ingredients[1]").value("Triple sec"))
                .andExpect(jsonPath("$[1].ingredients[2]").value("Lime juice"))
                .andExpect(jsonPath("$[1].ingredients[3]").value("Salt"))
                .andExpect(jsonPath("$[1].ingredients[4]").value("Blue Curacao"));
        verify(shoppingListService).getAllShoppingLists();
    }

    private List<ShoppingListResource> getShoppingListsMock() {
        List<ShoppingListResource> lists = new ArrayList<>();
        ShoppingListResource shoppingList = new ShoppingListResource(UUID.fromString("4ba92a46-1d1b-4e52-8e38-13cd56c7224c"), "Stephanie's birthday");
        Arrays.asList("Tequila",
                "Triple sec",
                "Lime juice",
                "Salt",
                "Blue Curacao").forEach(shoppingList::addIngredient);
        lists.add(shoppingList);
        shoppingList = new ShoppingListResource(UUID.fromString("6c7d09c2-8a25-4d54-a979-25ae779d2465"), "My birthday");
        Arrays.asList("Tequila",
                "Triple sec",
                "Lime juice",
                "Salt",
                "Blue Curacao").forEach(shoppingList::addIngredient);
        lists.add(shoppingList);
        return lists;
    }
}