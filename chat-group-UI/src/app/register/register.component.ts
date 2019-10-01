import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registerGroup: FormGroup;
  registerErrorMsg: string = "Please provide a proper alphanumeric username."
  registerError: boolean = false;

  constructor(
    private _formBuilder: FormBuilder
  ) { }

  ngOnInit() {

    this.registerGroup = this._formBuilder.group({
      chatUsername: [null, Validators.required]
    });

  }

  registerUser() {

    this.registerError = false;
    let registerUserDetails = this.registerGroup.getRawValue();
    if (registerUserDetails.chatUsername && registerUserDetails.chatUsername.length && registerUserDetails.chatUsername.length < 11 && /^[a-zA-Z0-9_]*$/gi.test(registerUserDetails.chatUsername)) {
      alert("proper username");
    }
    else {
      this.registerError = true;
    }

  }

}
