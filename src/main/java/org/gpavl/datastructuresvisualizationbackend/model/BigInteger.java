package org.gpavl.datastructuresvisualizationbackend.model;

import org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils.getIntArray;

public class BigInteger extends DataStructure {

    public BigInteger() {

    }

    public BigInteger(int number) {
        memoryHistory = new MemoryHistoryDto();
        OperationHistoryDto operationHistory = new OperationHistoryDto("BigInteger", Map.of(
                "number", number
        ));

        operationHistory.addLocalVariable("number", number);

        if (number < 0) {
            throw new IllegalArgumentException("Negative number not allowed");
        }

        if (number == 0) {
            operationHistory.addInstanceVariable("length", 1);

            List<Integer> array = Collections.nCopies(1, null);
            String newAddress = operationHistory.addNewObject(array);
            operationHistory.addInstanceVariable("digits", newAddress);

            List<Integer> newArray = new ArrayList<>(array);
            newArray.set(0, 0);
            operationHistory.updateObject(newAddress, newArray);
        }else {
            int length = 0;
            operationHistory.addInstanceVariable("length", length);

            int temp = number;
            operationHistory.addLocalVariable("temp", temp);

            while (temp > 0) {
                temp /= 10;
                operationHistory.addLocalVariable("temp", temp);

                length++;
                operationHistory.addInstanceVariable("length", length);
            }

            List<Integer> array = Collections.nCopies(length, null);
            String newAddress = operationHistory.addNewObject(array);
            operationHistory.addInstanceVariable("digits", newAddress);

            temp = number;
            operationHistory.addLocalVariable("temp", number);

            for (int i = 0; i < length; ++i) {
                array = new ArrayList<>(array);
                array.set(i, temp % 10);
                operationHistory.updateObject(newAddress, array);

                temp /= 10;
                operationHistory.addLocalVariable("temp", temp);
            }
        }

        operationHistory.removeLocalVariable("temp");
        operationHistory.removeLocalVariable("number");

        memoryHistory.addOperationHistory(operationHistory);
    }

    public void add(String number) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("add", memoryHistory, "number", number);

        operationHistory.addLocalVariable("number", number);

        validateString(number);
        convertToDigits(number, operationHistory);

        int length = (int) operationHistory.getInstanceVariableValue("length");
        int strLength = (int) operationHistory.getLocalVariableValue("strLength");
        String tempDigitsAddress = (String) operationHistory.getLocalVariableValue("tempDigits");
        List<Integer> tempDigits = operationHistory.getIntArray(tempDigitsAddress);
        List<Integer> digits = getIntArray(operationHistory, "digits");

        int maxLen = Math.max(length, strLength);
        operationHistory.addLocalVariable("maxLen", maxLen);

        List<Integer> array = Collections.nCopies(maxLen + 1, null);
        String newAddress = operationHistory.addNewObject(array);
        operationHistory.addLocalVariable("result", newAddress);

        int carry = 0;
        operationHistory.addLocalVariable("carry", carry);

        int i = 0;

        for (; i < maxLen; ++i) {
            operationHistory.addLocalVariable("i", i);

            int a = (i < length) ? digits.get(i) : 0;
            operationHistory.addLocalVariable("a", a);
            int b = (i < strLength) ? tempDigits.get(i) : 0;
            operationHistory.addLocalVariable("b", b);

            int sum = a + b + carry;
            operationHistory.addLocalVariable("sum", sum);

            array = new ArrayList<>(array);
            array.set(i, sum % 10);
            operationHistory.updateObject(newAddress, array);

            carry = sum / 10;
            operationHistory.addLocalVariable("carry", carry);
        }

        if (carry == 1) {
            array = new ArrayList<>(array);
            array.set(i, carry);
            operationHistory.updateObject(newAddress, array);

            i++;
            operationHistory.addLocalVariable("i", i);
        }

        operationHistory.freeInstanceVariable("digits", "digits freed");
        operationHistory.freeLocalVariable("tempDigits", "tempDigits freed");

        operationHistory.addInstanceVariable("digits", newAddress);
        operationHistory.addInstanceVariable("length", i);

        operationHistory.removeLocalVariable("sum");
        operationHistory.removeLocalVariable("b");
        operationHistory.removeLocalVariable("a");
        operationHistory.removeLocalVariable("i");
        operationHistory.removeLocalVariable("carry");
        operationHistory.removeLocalVariable("result");
        operationHistory.removeLocalVariable("maxLen");
        operationHistory.removeLocalVariable("strLength");
        operationHistory.removeLocalVariable("number");

        memoryHistory.addOperationHistory(operationHistory);
    }

    public void isGreaterThan(String number) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("isGreaterThan", memoryHistory, "number", number);
        operationHistory.addLocalVariable("number", number);

        validateString(number);

        int length = (int) operationHistory.getInstanceVariableValue("length");
        int strLength = number.length();
        List<Integer> digits = getIntArray(operationHistory, "digits");

        if (length != strLength) {
            operationHistory.addResult(length > strLength);
        }else {
            boolean found = false;
            for (int i = length - 1; i >= 0; i--) {
                int a = digits.get(i);
                operationHistory.addLocalVariable("a", a);

                int b = number.charAt(i) - '0';
                operationHistory.addLocalVariable("b", b);

                if (a != b) {
                    operationHistory.addResult(a > b);
                    found = true;
                    break;
                }
            }

            if (!found) {
                operationHistory.addResult(false);
            }
        }

        operationHistory.removeLocalVariable("b");
        operationHistory.removeLocalVariable("a");
        operationHistory.removeLocalVariable("number");
    }

    private void validateString(String number) {
        if (number.isEmpty()) throw new IllegalArgumentException("Invalid number");

        for (int i = 0; i < number.length(); i++) {
            if (!Character.isDigit(number.charAt(i))) {
                throw new IllegalArgumentException("Invalid number");
            }
        }

        if (number.charAt(0) == '0') throw new IllegalArgumentException("Invalid number");
    }

    private void convertToDigits(String number, OperationHistoryDto operationHistory) {
        int strLength = number.length();
        operationHistory.addLocalVariable("strLength", strLength);

        List<Integer> array = Collections.nCopies(strLength, null);
        String newAddress = operationHistory.addNewObject(array);
        operationHistory.addLocalVariable("tempDigits", newAddress);

        for (int i = 0; i < strLength; ++i) {
            array = new ArrayList<>(array);
            array.set(i, number.charAt(strLength - 1 - i) - '0');
            operationHistory.updateObject(newAddress, array);
        }
    }
}
