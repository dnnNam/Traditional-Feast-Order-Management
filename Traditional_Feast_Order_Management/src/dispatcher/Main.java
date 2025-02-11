package dispatcher;

import business.Customers;
import business.FeastMenus;
import business.Orders;
import java.util.ArrayList;
import model.Customer;
import model.FeastMenu;
import tools.Menu;

public class Main {

    public static void main(String[] args) {
        // tạo menu
        Menu menu = new Menu("Traditional Feast Order Management..");
        // thêm option nha 
        menu.addNewOption("Register customers");
        menu.addNewOption("Update customer information");
        menu.addNewOption("Search for customer information by name");
        menu.addNewOption("Display feast menus");
        menu.addNewOption("Place a feast order");
        menu.addNewOption("Update order information");
        menu.addNewOption("Save data to file");
        menu.addNewOption("Display Customer or Order lists");
        menu.addNewOption("Exit the Program");
        // tạo ra các anh quản lý 
        Customers cusManagement = new Customers();
        FeastMenus feastMenuManagement = new FeastMenus();
        Orders orderManagement = new Orders();
        // đọc file 
        cusManagement.readFromFile();
        feastMenuManagement.readFromFile();
        orderManagement.readFromFile();
        
        int choice = 0;
        while (true) {
            menu.print();
            choice = menu.getChoice();
            switch (choice) {
                case 1: {
                     cusManagement.addNewCustomer();
                break;
                   
                }
                case 2: {
                    cusManagement.updateCustomer();
                    break;
                }

                case 3: {
                    cusManagement.seachByName();
                    break;
                }
                case 4: {
                    feastMenuManagement.displayFeastMenus(feastMenuManagement.feaMenu);
                    break;
                }
                case 5: {
                    orderManagement.placeAFeastOrder(feastMenuManagement.feaMenu,cusManagement.cusList);
                        
                    
                    break;
                }
                case 6: {

                    break;
                }
                case 7: {
                    cusManagement.saveToFile();
                    System.out.println("successfully saved.");
                    break;
                }
                case 8: {

                    break;
                }
                case 9: {

                    break;
                }
                default: {
                    System.out.println("This function is not available");
                    break;
                }
            }

        }
    }
}
