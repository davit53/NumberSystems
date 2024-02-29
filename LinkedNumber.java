/**
 * @author Davit Najaryan
 * @date Febuary 20, 2024
 * This class is used to represent a positive whole number of any number system
 */

import java.lang.Math;

public class LinkedNumber {

    private int base;
    private DLNode<Digit> front;
    private DLNode<Digit> rear;

    /**
     * Constructor
     * @param num
     * @param baseNum
     */
    public LinkedNumber(String num, int baseNum){

        base = baseNum;

        if(num.equalsIgnoreCase("") == true){
            throw new LinkedNumberException("no digits given");
        }
        char[] charOfArrays = num.toCharArray();


        front = null;
        rear = null;

        for (int i = 0; i < charOfArrays.length; i++) {
            
            Digit curr = new Digit(charOfArrays[i]);
            DLNode<Digit> myCurrentNode = new DLNode<>(curr);

            if(front != null){
                rear.setNext(myCurrentNode);
                myCurrentNode.setPrev(rear);
                rear = myCurrentNode;
            }
            else{
                rear = myCurrentNode;
                front = myCurrentNode;
            }

        }

    }

    /**
     * Constructor for decimal number system
     * @param num
     */
    public LinkedNumber(int num){

        this(String.valueOf(num), 10);

    }

    /**
     * 
     * @return true if number stored in this linked list is a valid positive number
     */
    public boolean isValidNumber(){

        boolean valid = false;

        //take first node
        DLNode<Digit> curr = front;
        Digit myDigitObj;
        int num;

        while(curr != null){

            myDigitObj = curr.getElement();
            num = myDigitObj.getValue();

            if(num >= 0 && num < base){
                valid = true;
            }
            else{
                return false;
            }

            curr = curr.getNext();

        } 
        return valid;
        
    }

    /**
     * @return value of base
     */
    public int getBase(){
        return base;
    }

    /**
     * @return front node
     */
    public DLNode<Digit> getFront(){
        return front;
    }

    /**
     * @return rear node
     */
    public DLNode<Digit> getRear(){
        return rear;
    }

    /**
     * @return number of nodes
     */
    public int getNumDigits(){
        int counter = 0;

        DLNode<Digit> curr = front;

        while(curr != null){
            counter++;
            curr = curr.getNext();
        }

        return counter;

    }

    /**
     * @return return a String containing all the digits of the number (no spaces)
     */
    public String toString(){

        String myNum = "";

        DLNode<Digit> curr = front;
        Digit myDigit;

        while(curr != null){

            myDigit = curr.getElement();

            myNum += myDigit;

            curr = curr.getNext();

        }

        return myNum;

    }

    /**
     * 
     * @param other
     * @return false if num of digits arent the same or if bases are different
     * @return true if node are same and in same order
     */
    public boolean equals(LinkedNumber other){

        //check num of digits
        if(this.getNumDigits() != other.getNumDigits()){
            return false;
        }

        //check if same base
        if(this.base != other.base){
            return false;
        }

        DLNode<Digit> curr = this.front;
        Digit myDigit;
        int num;

        DLNode<Digit> currOther = other.front;
        Digit otherDigit;
        int otherNum;

        while(curr != null && currOther != null){ //"&&" bacuse we already checked that they must be same length

            myDigit = curr.getElement();
            num = myDigit.getValue();

            otherDigit = currOther.getElement();
            otherNum = otherDigit.getValue();

            if(num != otherNum){
                return false;
            }

            curr = curr.getNext();
            currOther = currOther.getNext();

        }
        return true;
        
    }

    /**
     * 
     * @param newBase
     * @return converted number into the specified base fromat
     */
    public LinkedNumber convert(int newBase){

        if(this.isValidNumber() == false){
            throw new LinkedNumberException("cannot convert invalid number");
        }
        else{
            //new LinkedNumber object that we need to return (will hold the converted number)

            int val = 0;
            int index = 0;
            int remainder;
            String checkString = "";
            String newVal = "";

            DLNode<Digit> curr = rear;
            Digit myDigit;
            int myDigitValue;

            //step 1
            while(curr != null){

                myDigit = curr.getElement();
                myDigitValue = myDigit.getValue();

                double temp = myDigitValue * Math.pow(base, index);

                val += temp;

                index++;

                curr = curr.getPrev();

            }

            //step 2
            while(val != 0){

                remainder = val % newBase;
                checkString = String.valueOf(remainder);
                if(checkString.equalsIgnoreCase("10")){
                    newVal = "A" + newVal;
                }
                else if(checkString.equalsIgnoreCase("11")){
                    newVal = "B" + newVal;
                }
                else if(checkString.equalsIgnoreCase("12")){
                    newVal = "C" + newVal;
                }
                else if(checkString.equalsIgnoreCase("13")){
                    newVal = "D" + newVal;
                }
                else if(checkString.equalsIgnoreCase("14")){
                    newVal = "E" + newVal;
                }
                else if(checkString.equalsIgnoreCase("15")){
                    newVal = "F" + newVal;
                }
                else{
                    newVal = checkString + newVal;
                }

                val /= newBase;

            }

            //step 3
            LinkedNumber convertedNum = new LinkedNumber(newVal, newBase);
            return convertedNum;

        }

    }

    /**
     * adds a node to the correspoding position
     * @param digit
     * @param position
     */
    public void addDigit (Digit digit, int position){

        if(position < 0 || position > this.getNumDigits()){
            throw new LinkedNumberException("invalid position");
        }

        DLNode<Digit> curr = new DLNode<>(digit);

        //check if list is empty
        if(this.getNumDigits() == 0){
            front = curr;
            rear = curr;
        }
        else if(position == 0){
            //add digit after rear
            rear.setNext(curr);
            curr.setPrev(rear);
            rear = curr;
        }
        else if(position == this.getNumDigits()){
            //add digit before front
            front.setPrev(curr);
            curr.setNext(front);
            front = curr;
        }
        else{

            //add digit at same place
            DLNode<Digit> temp = rear;

            for (int i = 0; i < position-1; i++) {
                temp = temp.getPrev();
            }

            DLNode<Digit> tempPrevious = temp.getPrev();

            temp.setPrev(curr);
            curr.setNext(temp);

            tempPrevious.setNext(curr);
            curr.setPrev(tempPrevious);
            
        }
    }

    /**
     * removes a node from a correspoding position
     * @param position
     * @return
     */
    public int removeDigit(int position){

        if(position < 0 || position > this.getNumDigits()-1){
            throw new LinkedNumberException("invalid position");
        }

        //rear node removal
        if(position == 0){
            int num = rear.getElement().getValue();
            rear = rear.getPrev();
            rear.setNext(null);
            return (int) (num * Math.pow(base, position));
            
        }
        //front node removal
        else if(position == this.getNumDigits()-1){
            int num = front.getElement().getValue();
            front = front.getNext();
            front.setPrev(null);
            return (int) (num * Math.pow(base, position));

        }
        //internal node removal
        else{
            DLNode<Digit> nodeToBeRemoved = rear;
            for (int i = 0; i < position; i++) {
                nodeToBeRemoved = nodeToBeRemoved.getPrev();
            }

            DLNode<Digit> before = nodeToBeRemoved.getPrev();
            DLNode<Digit> after = nodeToBeRemoved.getNext();

            int num = nodeToBeRemoved.getElement().getValue();

            after.setPrev(before);
            before.setNext(after);

            return (int) (num * Math.pow(base, position));
        }

    }

}
