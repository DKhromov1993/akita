/**
 * Copyright 2017 Alfa Laboratory
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.alfabank.core;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.alfabank.tests.core.drivers.CustomDriverProvider;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.Matchers.containsString;


class CustomDriverProviderTests {
    private CustomDriverProvider customDriverProvider;
    private WebDriver currentDriver;

    @BeforeEach
    void setUp() {
        customDriverProvider = new CustomDriverProvider();
    }

    @Test
    void createChromeDriverTest() {
        System.setProperty("browser", "chrome");
        System.clearProperty("browserVersion");
        currentDriver = customDriverProvider.createDriver(new DesiredCapabilities());
        assertThat(currentDriver.getClass().getName(), is("org.openqa.selenium.chrome.ChromeDriver"));
        currentDriver.quit();
    }

    @Test
    void createChromeDriverTestWithVersion() {
        System.setProperty("browser", "chrome");
        System.setProperty("browserVersion", "2.33");
        currentDriver = customDriverProvider.createDriver(new DesiredCapabilities());
        Map<String, String> chromeCap = (Map<String, String>) ((ChromeDriver) currentDriver).getCapabilities().getCapability("chrome");
        assertThat(currentDriver.getClass().getName(), is("org.openqa.selenium.chrome.ChromeDriver"));
        assertThat(chromeCap.get("chromedriverVersion"), containsString(System.getProperty("browserVersion")));
    }

    @Test
    @Disabled
    void createFirefoxDriverTest() {
        System.setProperty("browser", "firefox");
        currentDriver = customDriverProvider.createDriver(new DesiredCapabilities());
        assertThat(currentDriver.getClass().getName(), is("org.openqa.selenium.firefox.FirefoxDriver"));
    }

    @AfterEach
    void tearDown() {
        currentDriver.quit();
    }
}