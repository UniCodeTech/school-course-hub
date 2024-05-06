# School Course Hub Application

## Overview

The School Course Hub application is designed for educational institutes to manage courses, branches, and user registrations. It provides three types of users: Admin, Guest, and Signed-in users. Each user has specific functionalities within the application.

- **Admin**: Admin users have access to functionalities such as adding new courses, adding new branches, viewing user lists, viewing enrolled student lists, and updating course details.

- **Guest User**: Guest users can view all the details about the courses available in the institute. However, they cannot register for any course.

- **Signed-in User**: Signed-in users have access to all course details and can register for courses. They can also enter a promo code during registration to receive discounts. Upon clicking "Pay Now," they receive a confirmation email.

The application includes sign-up and sign-in pages. When a user signs up, they receive a verification code via email for account verification. The email sender functionality is not included in the GitHub repository.

## Features

- **Admin Features**:
  - Add new courses
  - Add new branches
  - View user lists
  - View enrolled student lists
  - Update course details

- **Guest User Features**:
  - View course details

- **Signed-in User Features**:
  - View course details
  - Register for courses
  - Enter promo codes for discounts
  - Receive confirmation emails upon registration

## Technologies Used

- Android Development (Java)
- SQLite Database
- Google Maps API

## User Interface Display

<div style="display: flex; flex-direction: row; justify-content: space-around; align-items: center;">
    <img src="/images/admin_home.png" alt="Admin Dashboard" width="200" height="auto">
    <img src="/images/Guest_Home.png" alt="Guest Course View" width="200" height="auto">
    <img src="/images/Course_Overview.png" alt="Course Overview" width="200" height="auto">
    <img src="/images/pay_now.png" alt="Signed-in User Course Registration" width="200" height="auto">
</div>

*Admin Dashboard* | *Guest User Course View* | *Course Overview* | *Signed-in User Course Registration*


## Future Enhancements

- **User Profile Picture Uploading Function**: Allow users to upload and manage their profile pictures.
  
- **Notification Function**: Implement a notification system to notify users about course updates, promotions, and other relevant information.
  
- **Check Nearest Branch Location**: Utilize the user's current location to provide information about the nearest branch location, making it easier for users to find and access the institute's branches.

## Contributors

This project was developed by contributors from [UniCodeTech](https://github.com/UniCodeTech).

Happy Coding!
