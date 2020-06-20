package xyz.vadviktor

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream
import java.net.URL
import java.nio.file.*
import java.util.concurrent.TimeUnit


val driver: WebDriver = ChromeDriver()
const val userFullName = "Mark VAD"
const val userEmail = "vad.mark.09@gmail.com"
val bookIds = setOf(
//    2693, 4131, 11128, 16846, 19593, 20357, 26832, 34511, 1394,
    34560, 1395,
    2694, 4132, 11126, 16876, 16884, 19596, 20358, 20376, 20377, 26833, 34017,
    1400, 1401, 1407, 1408, 1413, 1414, 4146, 4147, 7424, 7425, 1319, 34477,
    1311, 1353, 1354, 1393, 1399, 1405, 1412, 2695, 4133, 11127, 16908, 19597,
    20359, 20378, 20379, 26834, 26852, 34026, 34478
)
const val baseUrl = "https://my.cjfallon.ie/preview/student/"


fun main() {
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS)

    try {
        for (bookId in bookIds) {
            driver.get("${baseUrl}${bookId}")
            println("${driver.title} - $bookId")
            acceptPolicy()

            try {
                while (true) {
                    download()
                    driver.findElement(By.id("next")).click()
                }
            } catch (e: org.openqa.selenium.NoSuchElementException) {
                println("reached end of book")
                continue
            }
        }
    } finally {
        driver.quit()
    }
}

fun acceptPolicy() {
    try {
        driver.findElement(By.id("policy"))

        driver.findElement(By.id("name")).sendKeys(userFullName)
        driver.findElement(By.id("email")).sendKeys(userEmail)
        driver.findElement(By.id("policy")).click()
        driver.findElement(By.id("submit")).click()
    } catch (e: org.openqa.selenium.NoSuchElementException) {
        return
    }
}

fun download() {
    val book = driver.findElement(By.id("book"))
    val fileUrl = book.findElement(By.tagName("img")).getAttribute("src")
    val fileName = fileUrl.substringAfterLast("/")
    val sep = FileSystems.getDefault().separator
    val filePath = "books$sep${driver.title}$sep$fileName"
    File(filePath).mkdirs()
    try {
        val inStr: InputStream = URL(fileUrl).openStream()
        Files.copy(inStr, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING)
    }catch (e: FileNotFoundException){
        File(filePath).deleteRecursively()
        println("can't find $fileUrl")
    }
}

