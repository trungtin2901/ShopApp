import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';

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
  constructor(){
    this.phone = '';
    this.password = '';
    this.retypePassword = '';
    this.fullName = '';
    this.address = '';
    this.isAccepted = false;
    this.dateOfBirth = new Date();
    this.dateOfBirth.setFullYear(this.dateOfBirth.getFullYear() - 18);
    
  }
  onPhoneChange(){
    console.log(`Phone type: ${this.phone}`);
  }
  register(){
    const message = `Phone: ${this.phone}` +
                    `password: ${this.password}` +
                    `retypePassword: ${this.retypePassword}` +
                    `fullname: ${this.fullName}` +
                    `address: ${this.address}` +
                    `isAccepted: ${this.isAccepted}` +
                    `dateOfBirth: ${this.dateOfBirth}`;
    alert(message)
  }
  checkPasswordMatch(){
    if(this.password != this.retypePassword){
      this.registerForm.form.controls['retypePassword'].setErrors({'passwordMisMatch': true});
    }
    else{
      this.registerForm.form.controls['retypePassword'].setErrors(null);
    }
  }
}
