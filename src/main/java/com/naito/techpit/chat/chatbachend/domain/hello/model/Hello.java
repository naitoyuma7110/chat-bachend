package com.naito.techpit.chat.chatbachend.domain.hello.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Hello {
// @AllArgsConstructorによりフィールドを初期化するためのコンストラクターが自動生成
  private String message;
}


