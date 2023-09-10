/************************
 * Project: Java Marking System
 * Programmer: Masood Ashrafi
 * Date: February 04, 2021 
 * Program: markingsystem.java
 ************************/
package markingsystem;

import java.util.Scanner;//Needed for Scanner 
import java.text.*;//Needed for Decimal Formatter
import java.util.Arrays;//Needed for "Arrays.sort" and other functions and calculations  

public class markingsystem {

     //Interrupted exception below so that we can use thread.sleep for a loading screen 
    public static void main(String[] args) throws InterruptedException{
        try (Scanner scanN = new Scanner(System.in)) {
            try (Scanner scanS = new Scanner(System.in)) {
                DecimalFormat twoDigit = new DecimalFormat("0.00");//Create and set the Decimal Formatter to two digits 
                
                //Variables in main method
                int value;
                int num = 0;
                String grade; 
                String name; 
                String course; 
                String title = "Welcome to the Average Atlas.";
                
                for(int i = 0; i < title.length(); i++)
                {
                    System.out.print(title.charAt(i) + " ");
                    Thread.sleep(250);//Pauses the current thread for the given period of time to make a loading effect
                }
                
                System.out.println("\n\nYour #1 source for calulating marks and averages in school and beyond.");
                
                System.out.println("\nPlease enter your name:");//Ask the user to enter their name
                name = scanS.nextLine();//Store name in name variable 
                
                System.out.println("Please enter the grade of students you are currently teaching:");//Ask the user what grade they are teaching
                grade = scanS.nextLine(); //Store this value in the grade variable 
                
                System.out.println("Please enter your course code:");//Ask the user what the course code for their class is
                course = scanS.nextLine();//Store the course code in a variable as well
                
                do//Error Trap if they type in 0 or a negative number
                {
                    System.out.println("\nHow many students are in your class? \nPlease type in valid number of students (1 or more):");
                    num = scanN.nextInt();//Store this value in the num variable
                
                } while(num<=0);
                
                String[] names = new String[num];//Names array to be used for all the classroom names
                            
                double[] values = new double[num];//Values array to be used for all the classroom grades
                            
                AddNames(names, values);
                
                do 
                {
                System.out.println("\nPlease choose from the following menu:");
                System.out.println("1. Remove Students");
                System.out.println("2. Edit Grades");
                System.out.println("3. Your Information");
                System.out.println("4. Edit your information");
                System.out.println("5. Final Report");
                value = scanN.nextInt(); 
                
                switch(value)
                    {
                        case 1: 
                        {
                            int index;
                            
                            index = RemoveNames(names, values);//Make the index variable equal to the RemoveNames method
                            
                            
                            if(index >= 0)//If they have a positive index greater than or equal to 0
                            {
                                System.out.println("\nThe student has now been removed from your classroom.");
                                names[index] = "";//Set their name to essentially blank or nothing 
                                values[index] = -1;//Set their index value to -1 (can't do 0 because a student might actually have a mark of 0)
                            }
                            else 
                            {
                                System.out.println("\nThis student does not exist in your classroom. Returning to main screen...");//If they type an invalid user 
                            }
                            break;
                        } 
                        case 2:
                        {
                            int index; 
                         
                            index = EditGrades(names, values);//Make the index variable equal to the EditGrades method 
                            
                            if(index >=0)//If they have a positive index greater than or equal to 0
                            {
                                do//Error Trap if they type in a negative mark or a mark over 100
                                { 
                                    System.out.println("\nPlease type in the new mark (between 0 and 100) for " + names[index] + ":");
                                    values[index] = scanN.nextDouble();
                                } while(values[index] < 0 || values[index] > 100);
                                System.out.println("\n" + names[index] + "'s grade is now updated to " + values[index] + "%");
                            }
                            else 
                            {
                                System.out.println("\nThis student does not exist in your classroom. Returning to main screen...");
                            }
                            break;
                        }  
                        case 3:
                        {
                            Info(name, grade, course);
                            break;
                        }
                        case 4: 
                        {
                            EditInfo(name, grade, course);
                            break;
                        } 
                        case 5:
                        {
                            
                            Info(name, grade, course);
                            ClassList(names, values);
                            
                            //Intializing all the variables 
                            double avg = 0; 
                            double median = 0;
                            double mode = 0;
                            double range = 0; 
                            double variance = 0;
                            double std = 0;
                           
                            //Setting all the variables equal to their repsective Method 
                            avg = Average(values);
                            median = Median(values);
                            mode = Mode(values);
                            range = Range(values);
                            variance = Variance(values);
                            std = StandardDev(values);
                            
                            //Calling those returned values with a decimal formatter so everything is set to two decimal places 
                            System.out.println("\nAverage mark in the class: " + twoDigit.format(avg) + "%");
                            System.out.println("Median mark in the class: " + twoDigit.format(median) + "%");  
                            System.out.println("Mode or most common grade: " + twoDigit.format(mode) + "%");
                            System.out.println("The range of the grades: " + twoDigit.format(range) + "%"); 
                            System.out.println("The variance of the grades: " + twoDigit.format(variance));
                            System.out.println("The standard deviation of the class: " + twoDigit.format(std) + "\n");
                           
                            break;
                        }
                        default://If the user types in anything other than the numbers in the menu
                        {
                            Closing();
                            break;
                        }
                    }
                }
                while(value!=5);
            }
        }

    }//end of main
    
    /**
     * AddNames
     * This method has two for loops iterating through the number students and number array lengths asking the user to assign a name and grade for each student
     * @param students - The students argument is referenced here so we can choose any string array 
     * @param numbers - The numbers argument is reference here as well so that we can choose any double array 
     */
    public static void AddNames(String[] students, double[] numbers)
    {
        try (Scanner scanN = new Scanner(System.in)) {
            try (Scanner scanS = new Scanner(System.in)) {
                for(int i = 0; i < students.length; i++)
                {
                    System.out.println("\nPlease type in the name for student #" + (i + 1) + ":");
                    students[i] = scanS.nextLine();//Stores the name in the particular index in the array 
                }
            }
            
            for(int i = 0; i < numbers.length; i++)
            {
                do//Error Trap if they type in a negative mark or a mark over 100
                {
                    System.out.println("\nPlease type in the overall mark (between 0 and 100) \nfor " + students[i] + " rounded to two decimal places if possible:"); 
                    numbers[i] = scanN.nextDouble();//Stores the mark in the specific index in the array that is being iterated through at the moment 
                }
                while(numbers[i] < 0 || numbers[i] > 100);
            }
        }
        
    }//end of AddNames
    
    /**
     * RemoveNames
     * This method allows the user to remove any name in the array if the index in the names array equals the name the student typed in
     * @param names - The names argument is passed in here so that we can choose any String array 
     * @param numbers - The numbers argument is passed in here so that we can choose any double array 
     * @return index - The index of the name they want to remove in the names array 
     */
    public static int RemoveNames(String[] names, double[] numbers)
    {
        try (Scanner scanS = new Scanner(System.in)) {
            String studentname;
    
            System.out.println("\nWhich student would you like to remove?"); 
            studentname = scanS.nextLine();
            
            for(int index = 0; index < names.length; index++)
            {
                if(names[index].equalsIgnoreCase(studentname))//If the name they type in is equal to an index in the names array 
                {
                   return index;//Return that index value
                } 
            }
        }
        return -1;//Return -1 or nothing if the above statement is false
    }//end of RemoveNames
   
    /**
     * EditGrades
     * This method allows the user to enter a name and change the value of the grade (in the values array) associated with that index or student name in the names array
     * @param names  - The names argument is passed in here so that we can choose any String array 
     * @param numbers - The numbers argument is passed in here so that we can choose any double array 
     * @return index - The index of the name they want to remove in the names array 
     */
    public static int EditGrades(String[] names, double[] numbers)
    {
        try (Scanner scanS = new Scanner(System.in)) {
            String studentname;
                        
            System.out.println("\nWhich student's grade would you like to edit?"); 
            studentname = scanS.nextLine();
            
            for(int index = 0; index < names.length; index++)
            {
                if(names[index].equalsIgnoreCase(studentname))//If the name they typed in is a valid index in the names array 
                {
                   return index;//Return that index value
                } 
            }
        }
        return -1;//Return -1 or nothing if the above statement is false
    }//end of EditGrades
    
    /**
     * Info
     * This method prints out the course information
     * @param name - The name argument which is the name of teacher 
     * @param grade - The grade argument which is the grade they are teaching
     * @param course - The course argument which is the course code
     */
    public static void Info(String name, String grade, String course)
    {
        //The print statements below just list the name, grade, and course variables the user types in take from the main method as parameters
        System.out.println("\nTeacher Name: " + name);
        System.out.println("Grade: " + grade);
        System.out.println("Course Code: " + course); 
    }//end of Info 
    
    /**
     * EditInfo
     * This method allows the user or teacher to edit their class information 
     * @param name - The name argument which allows them to edit their name 
     * @param grade - The grade argument which allows them to update the grade that they are teaching
     * @param course - The course argument which allows the user to update their course code 
     */
    public static void EditInfo(String name, String grade, String course)
    {
        try (//Update User Information 
        Scanner scanS = new Scanner(System.in)) {
            String choice; 
            
            System.out.println("\nDo you want to change your account Name, Grade, or Course? Please type \nin \"Name\"/\"New Name\" or \"Grade\"/\"Class\" or \"Course\"/\"Course Code\". \nType in any other character if you would like to return to the main screen."); 
            choice = scanS.nextLine(); 

            if(choice.equalsIgnoreCase("Name") || choice.equalsIgnoreCase("New Name"))//If they type in any approved name response, ask them to update their name below
            {
                System.out.println("\nPlease enter your new name:");
                name = scanS.nextLine(); 
                System.out.println("\nYour name is updated to: " + name);
            } else if (choice.equalsIgnoreCase("Course") || choice.equalsIgnoreCase("Course Code") || choice.equalsIgnoreCase("CourseCode"))
            {
                System.out.println("\nPlease enter your new course code:");//Else If they type in any approved course code response, ask them to update their course code
                course = scanS.nextLine();
                System.out.println("\nThe course code is now updated to: " + course);
            } else if (choice.equalsIgnoreCase("Grade") || choice.equalsIgnoreCase("Class"))//Else if they type in any approved grade response, ask them to update their grade below
            {
                System.out.println("\nPlease enter your new grade:");
                grade = scanS.nextLine();
                System.out.println("\nThe grade/class is now updated to: " + grade);
            } else 
            {
                System.out.println("\nInvalid Response....Returning to the main screen.");//Otherwise, return back to the main screen 
            }
        }
    }//end of EditInfo 
    
    /**
     * Closing
     * This method prints out a prompt telling the user they typed an invalid response in the menu, sending them back to type an appropriate response
     */
    public static void Closing()
    {
        System.out.println("\nSorry, that is not an option.");
        System.out.println("Please try again.");
    }//end of Closing
    
     /** Average
     * This method takes any double array and prints out the average
     * @param numbers - The numbers argument is used here again so that we are able to choose any array
     * @return average - The average of all the double values in a given set of data
     */
    public static double Average(double[] numbers)
    {
        double values = 0; 
        double average = 0;
        int count = 0;
        
        for(int i = 0; i < numbers.length; i++)
        {
            if(numbers[i] != -1)//If the number index (mark) is not equal to -1 or removed
        {
            count++;//Increase the count variable by one each time for each valid mark
            values = values + numbers[i];//Adds the total of all the double values in the array
        }
        }
        average = values/count;//Divides the total by the length of the count variable to get the average in a new variable 
        return average;
    }//end of Average
    
    /**
     * Median
     * This method takes any double array and prints out the median of the dataset
     * @param numbers - The numbers argument allows us to choose and pass in any double array
     * @return median - The median (middle number) of all the double values in a given set of data
     */
    public static double Median(double[] numbers)
    {
        double median = 0; 
        int count = 0;
        int amount = 0; 
        
        Arrays.sort(numbers);//Sorts array from least to greatest
        
        for(int i = 0; i < numbers.length; i++)
        {
            if(numbers[i] != -1)//If the number index (mark) is not equal to -1 or removed
            {
                count++;//Increase the count variable by one each time for each valid mark
            }
        }
        amount = numbers.length - count;//Increase the count variable by one each time for each valid mark
        
        if(count % 2 != 0)//If the dataset has an odd number of elements
                {
                    median = numbers[count/2 + amount];//Find the middle value of the total number of elements using count/2
                } 
                else//In any other case when there is an even number of elements in the array 
                {
                    //The code in the line below finds the right middle value and left middle value using -1 and adds them and divides by 2 for average  
                    median = (numbers[count/2 + amount] + numbers[count/2-1 + amount])/2.00; 
                }
        
        return median;
    }//end of Median
    
    /**
     * Mode
     * This method takes any double array and prints out the mode or most common value
     * @param numbers - The numbers argument allows us to choose and pass in any double array
     * @return mode - The most common/reccuring value in the array 
     */
    public static double Mode(double[] numbers)
    {
        double mode = -1; 
        int maxAppearances = -1;
        
        /*This nested for loop below essentially uses the two variables above;
        the mode variable which holds the numbers and maxAppearances which holds how many times 
        that number appears in the array. The first for loop starts with the first
        double value in the array iterating through each element and the second array 
        also iterates through each element but instead keeps track of how many times 
        each number shows up rather than just holding the number itself. If it finds 
        another occurence while iterating, the count variable is increased by one 
        using count++.
        
        Then it keeps looping through every single double value in the array and if the 
        number has more appearances than the last number, that new value is set as mode.
        The appearances is kept track of because it is acutally to the count variable in
        the if statement. 
        
        Fianlly, the mode variable is returned to the main method.
        */
        for(int i = 0; i < numbers.length; i++)
        {        
            int count = 0;
            
            for(int j = 0; j < numbers.length; j++)
            {
                if(numbers[i] != -1 && numbers[i] == numbers[j])
                    count++;
            }
            
            if(count > maxAppearances)
            {
                mode = numbers[i]; 
                maxAppearances = count;
            }
            
        }
        return mode;
    }//end of Mode
    
    /**
     * Range 
     * This method takes any double array and prints out the range
     * @param numbers - The numbers argument allows us to choose and pass in any double array
     * @return range - The difference between the largest and smallest number in the array
     */
    public static double Range(double[] numbers) 
    {
        double range = 0;
        int count = 0;
        int amount = 0;
        
        Arrays.sort(numbers);//Sorts array from least to greatest
        
        for(int i = 0; i < numbers.length; i++)
        {
            if(numbers[i] != -1)//If the number index (mark) is not equal to -1 or removed
            {
                count++;//Increase the count variable by one each time for each valid mark
            }
        }
        
        amount = numbers.length - count;//The amount of -1s or removed grades
        
        //Takes the value of the last index value subtracted by the first index value and stores it in the range variable
        range = numbers[numbers.length-1] - numbers[0 + amount];
        return range;
    }//end of Range
   
    /**
     * Variance
     * This method tells us how spread the data is in relation to the mean
     * @param numbers - The numbers argument allows us to choose and pass in any double array
     * @return variance - The average of squared deviations from the mean
     */
    public static double Variance(double[] numbers)
    {
        //Intializing and setting up main variables 
        double mean;
        double variance = 0; 
        double sqDiff = 0;
        int count = 0;
        
        mean = Average(numbers);//Make the mean variable equal the Average method 
       
        for (int i = 0; i < numbers.length; i++)  
        {
            if(numbers[i] != -1)
            {
            count++;//Increase the count variable by one each time for each valid mark
            sqDiff += (numbers[i] - mean) *  (numbers[i] - mean); //This formula finds the sum squared and subtracts it by the mean
            variance = sqDiff/numbers[i]; 
            }
        }
        return variance;
    }//end of Variance
    
    /**
     * StandardDev 
     * This method tells us the Standard Deviation of the dataset (dispersion of the dataset relative to it's mean)
     * @param numbers - The numbers argument allows us to choose and pass in any double array
     * @return - The square root of the variance 
     */
    public static double StandardDev(double [] numbers)
    {
        double variance = 0;//Initalize a double variance variable 
        
        variance = Variance(numbers);//Make the variance variable equal the Variance method just above this method 
        
        double std = Math.sqrt(variance);//We can find the StandardDeviation here by square rooting the variance
        
        return std;
    }//end of StandardDev
    
    /**
     * ClassList
     * This method prints out the class list with names and grades in a table like format
     * @param names - - The names argument allows us to choose and pass in any String array
     * @param numbers - The numbers argument allows us to choose and pass in any double array
     */
    public static void ClassList(String[] names, double[] numbers)
    {
           System.out.println("\nName                    Grade");
           
           System.out.println("==============================");//Border of the banner
           for(int i = 0; i < names.length; i++)//For loop goes through the names array and prints out each name below
           {
               
               if(numbers[i] != -1)
               {
                  System.out.print(names[i]);//Print out the name for each index that is not -1 or removed 
                  if(names[i].length() < 8) 
                  {
                      System.out.println("\t\t\t" + numbers[i] + "%");//Print out the number (grade) for each index with name less than 8 characters
                  }
                  else if(names[i].length() >= 8 && names[i].length() <= 15)//Print out the number (grade) for each index with name between 8 and 15 characters
                  {
                      System.out.println("\t\t" + numbers[i] + "%");
                  }
                  else//If the names is any other length or greater than 15 characters, then tab 3 spaces
                  {
                      System.out.println("\t" + numbers[i] + "%");
                  }
               }
           }
           System.out.println("==============================");//Border of the banner  
           
    }//end of ClassList
    
}//end of class
