package database;

import java.util.Scanner;

import application.Account;
import application.Relationship;
import application.Resident;
import javafx.collections.ObservableList;


public class DatabaseConnectTest {

	public static void main(String[] args) {
		String username = null, password = null;
        System.out.println("Hi!");
        Scanner sc = new Scanner(System.in);
        username = sc.nextLine();
        password = sc.nextLine();
        ObservableList<Relationship> dataRelation = DatabaseConnecter.getRelationshipsData();
        if(dataRelation != null) {
        	for(Relationship res : dataRelation) {
        		System.out.println(res);
        	}
        } else {
        	System.out.print("Failed");
        }
	}

}
