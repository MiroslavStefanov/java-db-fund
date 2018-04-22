package com.softuni.gamestore;

import com.softuni.gamestore.model.dto.binding.GameAddBindingModel;
import com.softuni.gamestore.model.dto.binding.GameEditBindingModel;
import com.softuni.gamestore.model.dto.binding.UserLoginBindingModel;
import com.softuni.gamestore.model.dto.binding.UserRegisterBindingModel;
import com.softuni.gamestore.model.dto.view.DetailGameViewModel;
import com.softuni.gamestore.model.dto.view.UserLoginViewModel;
import com.softuni.gamestore.services.game.GameService;
import com.softuni.gamestore.services.order.OrderService;
import com.softuni.gamestore.services.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class CmdRunner implements CommandLineRunner {

    private final UserService userService;
    private final GameService gameService;
    private final OrderService orderService;

    private Long currentlyLoggedInUserId;
    private Set<String> shoppingCart;

    @Autowired
    public CmdRunner(UserService userService, GameService gameService, OrderService orderService) {
        this.userService = userService;
        this.gameService = gameService;
        this.orderService = orderService;
        currentlyLoggedInUserId = null;
        shoppingCart = new HashSet<>();
    }

    @Override
    public void run(String... args) throws Exception {

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        //Type exit to exit the main loop
        Scanner scanner = new Scanner(System.in);
        String cmd = scanner.nextLine();
        String[] arg;
        while(!cmd.toLowerCase().equals("exit")) {
            arg = cmd.split("\\|");
            switch (arg[0]) {
                case "RegisterUser":
                    UserRegisterBindingModel registerBindingModel = new UserRegisterBindingModel(arg[1], arg[2], arg[3], arg[4]);

                    boolean isRegistered = this.userService.register(registerBindingModel);
                    if (isRegistered)
                        System.out.println(registerBindingModel.getFullName() + " was registered");

                    break;

                case "LoginUser":
                    arg = cmd.split("\\|");
                    UserLoginBindingModel loginBindingModel = new UserLoginBindingModel(arg[1], arg[2]);

                    UserLoginViewModel loginViewModel = this.userService.login(loginBindingModel);
                    if (loginViewModel != null) {
                        this.currentlyLoggedInUserId = loginViewModel.getId();
                        System.out.println("Successfully logged in " + loginViewModel.getFullName());
                    } else
                        System.out.println("Incorrect username / password");

                    break;

                case "LogoutUser":
                    if (this.currentlyLoggedInUserId == null)
                        System.out.println("Cannot log out. No user was logged in.");
                    else {
                        String name = this.userService.logout(this.currentlyLoggedInUserId);
                        System.out.printf("User %s successfully logged out\r\n", name);
                        this.currentlyLoggedInUserId = null;
                        this.shoppingCart.clear();
                    }

                    break;

                case "AddGame":
                    if(!this.userService.isAdmin(this.currentlyLoggedInUserId))
                        break;

                    GameAddBindingModel addBindingModel = new GameAddBindingModel(arg[1],
                            new BigDecimal(arg[2]),
                            Float.parseFloat(arg[3]),
                            arg[4], arg[5], arg[6],
                            df.parse(arg[7]));

                    boolean isAdded =  this.gameService.add(addBindingModel);
                    if(isAdded)
                        System.out.println("Added " + addBindingModel.getTitle());

                    break;

                case "EditGame":
                    if(!this.userService.isAdmin(this.currentlyLoggedInUserId))
                        break;

                    GameAddBindingModel model = parseGameAddBindingModel(Arrays.copyOfRange(arg, 2, arg.length));
                    GameEditBindingModel editBindingModel = new GameEditBindingModel(Long.parseLong(arg[1]), model);

                    String title = this.gameService.edit(editBindingModel);

                    if(title != null && !title.equals("invalid"))
                        System.out.println("Edited " + title);
                    else if(title == null)
                        System.out.println("There is no game with ID " + editBindingModel.getId());

                    break;

                case "DeleteGame":
                    if(!this.userService.isAdmin(this.currentlyLoggedInUserId))
                        break;

                    Long id = Long.parseLong(arg[1]);
                    String deleteTitle = this.gameService.delete(id);

                    if(deleteTitle != null)
                        System.out.println("Deleted " + deleteTitle);
                    else
                        System.out.println("There is no game with ID " + id);

                    break;

                case "AllGame":
                    this.gameService.getAllGames().forEach(System.out::println);
                    break;

                case "DetailGame":
                    DetailGameViewModel detailModel = this.gameService.getDetailGame(arg[1]);
                    if(detailModel == null)
                        System.out.println("There is no game with name " + arg[1]);
                    else
                        System.out.println(detailModel);

                    break;

                case "OwnedGame" :
                    List<String> gameTitles = this.userService.getOwnedGames(this.currentlyLoggedInUserId);
                    if(gameTitles == null)
                        System.out.println("No user is logged in");
                    else if(gameTitles.size() > 0)
                        gameTitles.forEach(System.out::println);
                    else
                        System.out.println("No owned games are available");

                    break;

                case "AddItem":
                    if(this.currentlyLoggedInUserId == null)
                        System.out.println("No user is logged in");
                    else if(this.userService.hasGame(this.currentlyLoggedInUserId, arg[1]))
                        System.out.println("Item " + arg[1] + " is already owned");
                    else if(this.shoppingCart.contains(arg[1]))
                        System.out.println("Item " + arg[1] + " is already in the cart");
                    else if(!this.gameService.exists(arg[1]))
                        System.out.println("There is no item with name " + arg[1] + " in the game store");
                    else {
                        this.shoppingCart.add(arg[1]);
                        System.out.println(arg[1] + " added to the cart");
                    }

                    break;

                case "RemoveItem":
                    if(this.shoppingCart.contains(arg[1])) {
                        this.shoppingCart.remove(arg[1]);
                        System.out.println(arg[1] + " removed from the cart");
                    } else
                        System.out.println("There is no item with name " + arg[1] + " in the cart");

                    break;

                case "BuyItem":
                    if(this.shoppingCart.size() > 0) {
                        System.out.println("Successfully bought games:");
                        this.shoppingCart.forEach(x-> System.out.println("  -" + x));
                        this.orderService.createOrder(this.currentlyLoggedInUserId, this.shoppingCart);
                        this.userService.addGames(this.currentlyLoggedInUserId, this.shoppingCart);
                        this.shoppingCart.clear();
                    } else if(this.currentlyLoggedInUserId == null)
                        System.out.println("No user is logged in");
                    else
                        System.out.println("The cart is empty");

                    break;
            }
            cmd = scanner.nextLine();
        }
    }

    private GameAddBindingModel parseGameAddBindingModel(String[] source) {
        GameAddBindingModel model = new GameAddBindingModel();

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String[] prop;
        for (String s : source) {
            prop = s.split("=");
            switch (prop[0]) {
                case "title":
                    model.setTitle(prop[1]);
                    break;
                case "price":
                    model.setPrice(new BigDecimal(prop[1]));
                    break;
                case "size":
                    model.setSize(Float.parseFloat(prop[1]));
                    break;
                case "trailer":
                    model.setTrailer(prop[1]);
                    break;
                case "thumbnailURL":
                    model.setThumbnail(prop[1]);
                    break;
                case "description":
                    model.setDescription(prop[1]);
                    break;
                case "releaseDate":
                    try {
                        model.setReleaseDate(df.parse(prop[1]));
                    } catch (ParseException e) {
                        System.out.println("Invalid date format");
                    }
                    break;
            }
        }
        return model;
    }
}
