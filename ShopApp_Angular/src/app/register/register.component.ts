import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  @ViewChild('registerForm') registerForm!: NgForm;
  //Khai báo các biến tương ứng với trường dữ liệu trong form
  phone: string;
  password: string;
  retypePassword: string;
  fullName: string;
  address: string;
  isAccepted: boolean;
  dateOfBirth: Date;
// Khởi tạo các giá trị mặc định cho các biến
constructor(
  private http: HttpClient, 
  private router: Router
  ) {
        this.phone = '123123'; 
        this.password = '123456'; 
        this.retypePassword = '123456';
        this.fullName = 'Huynh Trung Tin';
        this.address = 'Can Tho'; 
        this.isAccepted = true; 
        this.dateOfBirth = new Date(); 
        this.dateOfBirth.setFullYear(this.dateOfBirth.getFullYear() - 18); // Đặt ngày sinh là 18 năm trước ngày hiện tại
}

// Hàm được gọi khi giá trị của trường số điện thoại thay đổi
onPhoneChange(){
        console.log(`Phone type: ${this.phone}`); // In ra console số điện thoại hiện tại
}

// Hàm đăng ký người dùng
register(){
  // Tạo chuỗi thông báo chứa tất cả thông tin người dùng
  const message = `Phone: ${this.phone}` +
                  `password: ${this.password}` +
                  `retypePassword: ${this.retypePassword}` +
                  `fullname: ${this.fullName}` +
                  `address: ${this.address}` +
                  `isAccepted: ${this.isAccepted}` +
                  `dateOfBirth: ${this.dateOfBirth}`;

  // Địa chỉ API để đăng ký người dùng
  const apiUrl = "http://localhost:8088/api/v1/users/register";

  // Dữ liệu đăng ký để gửi đến API
  const registerData = {
      "fullname": this.fullName,
      "phone_number": this.phone,
      "address": this.address,
      "password": this.password,
      "retype_password": this.retypePassword,
      "date_of_birth": this.dateOfBirth,
      "facebook_account_id": 0,
      "google_account_id": 0,
      "role_id": 1
  }

  // Headers cho yêu cầu HTTP
  const headers = new HttpHeaders({
    'Content-Type': 'application/json', // Đặt loại nội dung của yêu cầu là JSON
  });

  // Gửi yêu cầu POST đến API để đăng ký người dùng
  this.http.post(apiUrl, registerData, {headers}, )
      .subscribe({
          next: (response: any) => {
              // Nếu yêu cầu thành công, chuyển hướng người dùng đến trang đăng nhập
              this.router.navigate(['/login']);
          },
          complete: () => {
              // Hàm này sẽ được gọi khi yêu cầu hoàn tất, dù thành công hay thất bại
          },
          error: (error: any) => {
              // Nếu có lỗi, hiển thị thông báo lỗi
              alert(`Cannot register, error: ${error.error}`);
          }
  })
}
  checkPasswordMatch(){
    if(this.password != this.retypePassword){
      this.registerForm.form.controls['retypePassword'].setErrors({'passwordMisMatch': true});
    }
    else{
      this.registerForm.form.controls['retypePassword'].setErrors(null);
    }
  }
  checkAge(){
      // Kiểm tra xem ngày sinh đã được đặt chưa
      if(this.dateOfBirth){
        // Lấy ngày hiện tại
        const today = new Date();
        // Chuyển đổi ngày sinh từ chuỗi sang đối tượng Date
        const birthDay = new Date(this.dateOfBirth);
        // Tính tuổi bằng cách lấy năm hiện tại trừ đi năm sinh
        var age = today.getFullYear() - birthDay.getFullYear();
        // Tính sự khác biệt về tháng giữa ngày hiện tại và ngày sinh
        const monthDiff = today.getMonth() - birthDay.getMonth();
        // Nếu tháng hiện tại nhỏ hơn tháng sinh, hoặc nếu tháng hiện tại bằng tháng sinh nhưng ngày hiện tại nhỏ hơn ngày sinh
        if(monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDay.getDate())){
          // Giảm tuổi đi 1 vì người dùng chưa đến sinh nhật trong năm nay
          age--;
        }
        // Nếu tuổi nhỏ hơn 18
        if(age < 18){
          // Đặt lỗi 'invalidAge' cho trường 'dateOfBirth' trong form đăng ký
          this.registerForm.form.controls['dateOfBirth'].setErrors({'invalidAge': true});
        }
        else{
          // Nếu tuổi lớn hơn hoặc bằng 18, xóa bất kỳ lỗi nào có thể đã được đặt trước đó cho trường 'dateOfBirth' trong form đăng ký
          this.registerForm.form.controls['dateOfBirth'].setErrors(null);
        }
      }
    }
    //viết hàm kiểm tra số điện thoại
    checkPhoneNumber(){
      //Kiểm tra số điện thoại có đúng 10 chữ số không
      if(this.phone.length != 10){
        //Nếu không đúng thì đặt lỗi 'invalidPhoneNumber' cho trường 'phone' trong form đăng ký
        this.registerForm.form.controls['phone'].setErrors({'invalidPhoneNumber': true});
      }
      else{
        //Nếu đúng thì xóa bất kỳ lỗi nào có thể đã được đặt trước đó cho trường 'phone' trong form đăng ký
        this.registerForm.form.controls['phone'].setErrors(null);
      }
    }
    
}
