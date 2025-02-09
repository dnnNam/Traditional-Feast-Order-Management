
package dispatcher;

import business.Customers;


public class Main {
    public static void main(String[] args) {
        Customers cusManagement = new Customers();
        cusManagement.addNewCustomer();
        cusManagement.seachByName();
    }
}
