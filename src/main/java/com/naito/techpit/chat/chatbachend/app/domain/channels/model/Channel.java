package com.naito.techpit.chat.chatbachend.app.domain.channels.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Channel {
  /*
   * @Data
   * 
   * - 各項目のgetter/setter
   * 
   * - クラスの等価性を検証するequals
   * 
   * - クラスのハッシュ値を計算するhashCode
   * 
   */

  private int id;
  private String name;

}
