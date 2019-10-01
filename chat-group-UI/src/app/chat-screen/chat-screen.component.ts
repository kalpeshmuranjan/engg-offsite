import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-chat-screen',
  templateUrl: './chat-screen.component.html',
  styleUrls: ['./chat-screen.component.css']
})
export class ChatScreenComponent implements OnInit {

  chatGroup: FormGroup;
  chatMessages: any = [];
  username: string = localStorage.getItem("username") || "Shrikant";
  constructor(
    private _formBuilder: FormBuilder
  ) {

    this.chatMessages = [
      {
        messageId: 1,
        fromUser: "shrikant",
        timeStamp: 1569929763,
        content: "Test shrikant"
      },
      {
        messageId: 2,
        fromUser: "prashnt",
        timeStamp: 1569929763,
        content: "Test prashant"
      },
      {
        messageId: 1,
        fromUser: "shankar",
        timeStamp: 1569929763,
        content: "Test shankar"
      },
      {
        messageId: 1,
        fromUser: "shrikant",
        timeStamp: 1569929763,
        content: "Test shrikant 2"
      },
      {
        messageId: 1,
        fromUser: "ajay",
        timeStamp: 1569929763,
        content: "Test ajay"
      }
    ];

  }

  ngOnInit() {


    this.chatGroup = this._formBuilder.group({
      chatMessage: [null, Validators.required]
    });

  }

  sendMessage() {

  }

}
