import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-chat-screen',
  templateUrl: './chat-screen.component.html',
  styleUrls: ['./chat-screen.component.css']
})
export class ChatScreenComponent implements OnInit {

  chatGroup: FormGroup;
  constructor(
    private _formBuilder: FormBuilder
  ) { }

  ngOnInit() {


    this.chatGroup = this._formBuilder.group({
      chatMessage: [null, Validators.required]
    });

  }

  sendMessage() {

  }

}
