package ca.jrvs.practice.codingChallenge;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;

import java.util.Locale;

/**
 * Ticket: https://www.notion.so/jarvisdev/Valid-Palindrome-4de6fcf60afa412ca6a881f07448c9fa
 */
public class ValidPalindrome {

    /**
     * This method solves the ValidPalindrome question using two pointers.
     * The method has O(n) time complexity where n is the length of the string
     * @param s string to check if its a palindrome
     * @return True if the string is a palindrome, false otherwise
     */
    public boolean pointerImp(String s){
        int start = 0;
        int end = s.length()-1;
        while (start < end){
            while (!Character.isAlphabetic(s.charAt(start))){
                start += 1;
            }
            while (!Character.isAlphabetic(s.charAt(end))){
                end -= 1;
            }
            if (s.toLowerCase().charAt(start) == s.toLowerCase().charAt(end)){
                start += 1;
                end -= 1;
            }
            else {
                return false;
            }
        }
        return true;
    }
}
