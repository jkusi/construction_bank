<?php
// Check if the user is logged in
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $username = $_POST["username"];
    $password = $_POST["password"];

    $hostname = "192.168.50.23";
    $db_username = "king";
    $db_password = "Kusi@123";
    $database = "bank";

    $conn = mysqli_connect($hostname, $db_username, $db_password, $database);
    if ($conn->connect_error) {
        die("Connection failed: " . $conn->connect_error);
    }

    // Store the credentials in the database
    $hashedPassword = password_hash($password, PASSWORD_DEFAULT);
    $sql = "INSERT INTO users (username, password) VALUES (?, ?)";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("ss", $username, $hashedPassword);
    $stmt->execute();

    // Redirect the user to the dashboard page
    header("Location: dashboard.html");
    exit();
}
?>
