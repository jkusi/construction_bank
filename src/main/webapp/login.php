<?php
// Check if the user is logged in
$username = $_POST["username"];
$password = $_POST["password"];

$hostname = "192.168.50.23";
$username = "king";
$password = "Kusi@123";
$database = "bank";

$conn = mysqli_connect($hostname, $username, $password, $database);
if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}

$sql = "SELECT * FROM users WHERE username='$username' AND password='$password'";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
  // The user is logged in, redirect them to the dashboard page
  header("Location: dashboard.html");
} else {
  // The user is not logged in, show an error message
  echo "Invalid username or password.";
}

$conn->close();
?>

