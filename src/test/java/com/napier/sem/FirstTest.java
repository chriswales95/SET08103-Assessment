package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class FirstTest {

    static App app;

    @BeforeAll
    static void init() {
        app = new App();
    }

    @Test
    void getUserChoice()
    {
        app.getUserChoice(); //I have absolutely no idea what to input here
    }
}