
package business;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import model.Customer;
import model.FeastMenu;
import model.Order;
import tools.Acceptable;
import tools.Inputter;


public class Orders {
    // anh quản lí các đơn hàng 
    public ArrayList<Order> orderList = new ArrayList<>();
   
    
     
     // hàm kiểm tra ngày tháng có trong tương lai không 
     public boolean isFutureDate(String dateString){
         try {
             SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);

            Date inputDate = sdf.parse(dateString);
            Date today = new Date();
            
            return inputDate.after(today);
        } catch (Exception e) {
            return false;
        }
     }
     
     
     
      public boolean checkisDupEventDate(String eventDate){
         for (Order order : orderList) {
             if(order.getEventDate().equals(eventDate)){
                 return false;  // dã bị trùng đơn hàng 
             }
         }
         return true; // không trùng 
     }
      
       // hàm kiểm tra xem coi đơn hàng đã được đặt chưa 
     public boolean checkisDupMenuCode(String menuCode){
         for (Order order : this.orderList) {
             if(order.getMenu_Id().equals(menuCode)){
                 return false;  // dã bị trùng đơn hàng 
             }
         }
         return true; // không trùng 
     }
     
    // hàm cho khách hàng đặt hàng 
    public void placeAFeastOrder (ArrayList<FeastMenu> fmManagement , ArrayList<Customer> cusManagement ){
        // kiểm tra xem khách hàng có đăng kí chưa 
        boolean ischeck = false;
         Customer cus = null;
        do {            
            ischeck = false;
            String cusId = Inputter.getString("Input your id: ",
              "Data is invalid! Re-enter...");
            // kiểm tra khách hàng có tồn tại hay không    
             for (Customer customer : cusManagement ) {
                if(customer.getCus_id().equals(cusId)){
                    cus = customer;
                   break;
                }
            }
             if(cus != null){
                 ischeck = true; 
             }
        } while (!ischeck);
           
        // kiểm tra xem mã thực đơn có đúng trong danh sách hay không 

         boolean isDup;
         FeastMenu fm = null;
         String codeMenu = "";
        do {   
            isDup = false;
            codeMenu = Inputter.getString("Input codeMenu you wanna to order: ",
           "Data is invalid! Re-enter...");
             for (FeastMenu menu : fmManagement) {
                if(menu.getMenuCode().equals(codeMenu)){
                    fm = menu;
                    break;
                }
            }
            if(fm != null){
                isDup = true;
            }else{
                System.out.println("Data is invalid! Re-enter... ");
            }
            
        } while (!isDup);
        
        // cho nhập số bàn cần đặt cho đơn hàng 
        int numberOfTable = Inputter.getAnInteger("Input your number of Table",
                "Data is invalid! Re-enter...",1 , 10000);
        // nhập ngày tổ chức sự kiện
       
        
        boolean isTrue = false;
        String eventDate = "";
        // kiểm tra xem do nhập ngày tháng đúng ko
        while (!isTrue) {   
            // nhập ngày tháng
             eventDate = Inputter.getString("Input your Event Date(dd/mm/yyyy): ", 
                "Data is invalid! Re-enter...", "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(20[2-9][0-9]|20[1-9][1-9])$");
             if (isFutureDate(eventDate)) {
                break;
            } else {
                System.out.println("Data is invalid! Re-enter... ");
            }
        }
        
        // đúc ra object order 
        String order_id = orderList.size() + 1 + "";
        double totalCost = fm.getPrice() * numberOfTable;
        
        Order newOrder = new Order(order_id, cus.getCus_id(), fm.getMenuCode(), eventDate, numberOfTable , totalCost);
       
        /// kiểm tra xem đơn hàng hoặc ngày đặt có bị trùng hay không 
       if(this.checkisDupMenuCode(codeMenu) && this.checkisDupEventDate(eventDate)){
           StringTokenizer st = new StringTokenizer(fm.getIngredients(), "#");
            String appetizer = st.nextToken().trim().substring(1);
            String mainCourse = st.nextToken().trim();
            String desert = st.nextToken().trim();
            String updateDesrt = desert.substring(0, desert.length() - 1);
            DecimalFormat formatter = new DecimalFormat("#,###,### vnd");
            String formattedFee = formatter.format(totalCost);
            String str2 = String.format(
                    
                    " ----------------------------------------------------------------"
                    + "Customer order information [Order ID: %s]"
                    + "---------------------------------------------------------------"
                    + "Code: %s\n"
                    + "Customer Name: %s\n"
                    + "PhoneNumber: %s\n"
                    + "Email: %s \n"
                    + "---------------------------------------------------------------"
                    + "Code of Set Menu:: %s\n"
                    + "Set menu name: %s\n"
                    + "Event date: %s\n"
                    + "Number of tables: %d \n"
                    + "Ingredients:\n"
                    + "%s\n"
                    + "   %s\n"
                    + "   %s\n"
                    + "------------------------------------------------------"
                    + "Total cost: %s"        
                    + "------------------------------------------------------",
                    newOrder.getOrder_Id(),
                    cus.getCus_id(), 
                    cus.getName(),
                    cus.getPhoneNumber(),
                    cus.getEmail(),
                    newOrder.getMenu_Id(),
                    fm.getMenuName() ,
                    newOrder.getEventDate(), 
                    newOrder.getNumberOfTable(), 
                    appetizer, 
                    mainCourse,
                    updateDesrt , 
                    formattedFee);
            System.out.println(str2);
            orderList.add(newOrder);
           
       }else{
           System.out.println("Dupplicate data !!!!");
       }
        
    }
    
    
     public void saveToFile() {
        // tạo object file 
         File f = new File("D:\\Lab211\\lab2\\Order.csv");
        try {
            // tạo ra anh ghi file 
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(f));
            // duyệt mảng 
            for (Order order : orderList) {
                writer.write(order.toString());
                writer.write("\n");
            }
            writer.flush(); // lưu rồi tắt

        } catch (Exception e) {
            System.out.println("File lỗi rồi nè: " + e);
        }
    }

    public Order dataToObject(String line) {

        StringTokenizer st = new StringTokenizer(line, ",");
 
        // xử lí lần lượt các propos 
        String order_Id = st.nextToken().trim();
        String cus_Id = st.nextToken().trim();
        String menu_Id = st.nextToken().trim();
        String eventDate = st.nextToken().trim();
        int  numberOfTable = Integer.parseInt(st.nextToken().trim()) ;
        double totalCost = Double.parseDouble(st.nextToken().trim()) ;
        // đức ra object và trả ra object
         Order newOrder = new Order(order_Id, cus_Id, menu_Id, eventDate, numberOfTable, totalCost);
        return newOrder;
    }

    // hàm đọc file 
    public void readFromFile() {
        File f = new File("D:\\Lab211\\lab2\\Order.csv");
        if (!f.exists()) {
            System.out.println("file not exist!!!");
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(f));
            String line = reader.readLine();
            while (line != null) {
                Order newOrder = dataToObject(line);

                if (newOrder != null) {
                    orderList.add(newOrder);
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("File lỗi rồi " + e);
        }
    }
}
