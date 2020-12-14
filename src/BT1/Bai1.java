package BT1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Bai1 {
    public static void main(String[] args) {
        String filename = "Employee.CSV";
        ArrayList<Employee> employeeList = new ArrayList<>(reaFile(filename));
        var choice = 0;
        var input = new Scanner(System.in);
        do {
            System.out.println("1: Them Nhan Vien");
            System.out.println("2: Hien Thi Nhan Vien");
            System.out.println("3: Tim Nhan Vien Theo Ten");
            System.out.println("4: Xoa Nhan Vien Theo Ma");
            System.out.println("5: Ghi File");
            System.out.println("0: Thoat Chuong Trinh");
            choice = input.nextInt();
            input.nextLine();
            switch (choice) {
                case 1:
                    var add = ceartEmloyee(input);
                    employeeList.add(add);
                    System.out.println("Them Thanh Cong");
                    break;
                case 2:
                    showEmloyee(employeeList);
                    break;
                case 3:
                    if (employeeList.size() > 0) {
                        var name = "";
                        System.out.println("Nhan Ten Cua Ban Muon Tim");
                        name = input.nextLine();
                        var result = getByName(employeeList, name);
                        if (result.size() > 0) {
                            System.out.println("Tim Thay " + result.size() + " Ket Qua");
                            showEmloyee(result);
                        } else {
                            System.out.println("Khong Tim Thay Nhan Vien Nao " + name + ": ");
                        }
                    } else {
                        System.out.println("Danh Sach Trong");
                    }
                    break;
                case 4:
                    if (employeeList.size() > 0) {
                        System.out.println("Nhap ma nhan vien muon Xoa");
                        var id = input.nextLine();
                        var result = removeID(employeeList, id);
                        if (result) {
                            System.out.println("Xoa Thanh Cong");
                        } else {
                            System.out.println("Xoa That Bai");
                        }
                    } else {
                        System.out.println("Danh Sach Trong");
                    }
                    break;
                case 5:
                    if (employeeList.size() > 0) {
                        var result = writeFile(employeeList, filename);
                        if (result) {
                            System.out.println("Ghi File Thanh Cong");
                        } else {
                            System.out.println("Ghi File That Bai");
                        }
                    } else {
                        System.out.println("Danh Sach Rong");
                    }
                    break;
            }
        } while (choice != 0);
    }

    private static boolean writeFile(ArrayList<Employee> employeeList, String filename) {
        var isFile = reaFile(filename);
        try {
            FileWriter fileWriter = new FileWriter(filename, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            var format = "dd/MM/yyyy";
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            for (Employee emp : employeeList) {
                if (!isExit(isFile, emp)) {
                    printWriter.println(emp.getId());
                    printWriter.println(emp.getFullName());
                    printWriter.println(emp.getAddress());
                    printWriter.println(dateFormat.format(emp.getDateOfBirth()));
                    printWriter.println(emp.getPhoneNumber());
                    printWriter.println(emp.getSalary());
                    printWriter.println(emp.getExperience());
                }
            }
            printWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static boolean isExit(ArrayList<Employee> isFile, Employee emp) {
        for (var item : isFile) {
            if (emp.getId().compareTo(item.getId()) == 0) {
                return true;
            }
        }
        return false;
    }

    private static boolean removeID(ArrayList<Employee> employeeList, String id) {
        for (int i = 0; i < employeeList.size(); i++) {
            if (employeeList.get(i).getId().compareTo(id) == 0) {
                employeeList.remove(i);
                return true;
            }
        }
        return false;
    }

    private static ArrayList<Employee> getByName(ArrayList<Employee> employeeList, String name) {
        ArrayList<Employee> list = new ArrayList<>();
        for (var emp : employeeList) {
            var fisrName = getFirstName(emp.getFullName());
            if (fisrName.compareToIgnoreCase(name) == 0) {
                list.add(emp);
            }
        }
        return list;
    }

    private static String getFirstName(String fullName) {
        var index = fullName.lastIndexOf(" ");
        return fullName.substring(index + 1);
    }

    private static void showEmloyee(ArrayList<Employee> employeeList) {
        if (employeeList.size() > 0) {
            var format = "dd/MM/yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            System.out.printf("%-15s%-15s%-15s%-15s%-15s%-15s%-15s\n",
                    "Ma NV", "Ten NV", "Dia Chi", "Nam Sinh", "So DT", "Luong", "Kinh Ngiem");
            for (var index : employeeList) {
                show(index, simpleDateFormat);
            }
        } else {
            System.out.println("Danh Sach Rong");
        }
    }

    private static void show(Employee index, SimpleDateFormat dateFormat) {
        System.out.printf("%-15s%-15s%-15s%-15s%-15s%-15s%-15s\n",
                index.getId(), index.getFullName(), index.getAddress(),
                dateFormat.format(index.getDateOfBirth()), index.getPhoneNumber(),
                index.getSalary(), index.getExperience());
    }

    private static ArrayList<Employee> reaFile(String filename) {
        ArrayList<Employee> employees = new ArrayList<>();
        var file = new File(filename);
        var format = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            file.createNewFile();
            var input = new Scanner(file);
            while (input.hasNextLine()) {
                var id = input.nextLine();
                var fullName = input.nextLine();
                var address = input.nextLine();
                var date = dateFormat.parse(input.nextLine());
                var phone = input.nextLine();
                var salary = input.nextFloat();
                var ex = input.nextFloat();
                Employee employee = new Employee(id, fullName, address, date, phone, salary, ex);
                employees.add(employee);
            }
            input.close();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return employees;
    }

    private static Employee ceartEmloyee(Scanner input) {
        System.out.println("Nhap Ten");
        var id = input.nextLine();
        System.out.println("Nhap Dia Chi");
        var address = input.nextLine();
        System.out.println("Ngay Thang Nam Sinh Vi Du: 20/20/2020 ");
        String format = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date da;
        try {
            da = simpleDateFormat.parse(input.nextLine());
        } catch (ParseException e) {
            e.printStackTrace();
            da = new Date();
        }
        System.out.println("Nhap So Dien Thoai");
        var phone = input.nextLine();
        System.out.println("Nhap Luong");
        var salary = input.nextFloat();
        System.out.println("Nhap Nhap So Nam Kinh Ngiem");
        var ex = input.nextFloat();
        return new Employee(null, id, address, da, phone, salary, ex);
    }
}
