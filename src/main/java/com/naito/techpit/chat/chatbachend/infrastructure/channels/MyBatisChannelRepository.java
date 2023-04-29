package com.naito.techpit.chat.chatbachend.infrastructure.channels;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import com.naito.techpit.chat.chatbachend.application.service.ChannelRepository;
import com.naito.techpit.chat.chatbachend.domain.channels.model.Channel;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MyBatisChannelRepository implements ChannelRepository {
  private final ChannelMapper channelMapper;

  @Override
  public void insert(Channel channel) {
    channelMapper.insert(channel);
  }

  @Override
  public List<Channel> findAll() {
    return channelMapper.findAll();
  }

  @Override
  public Optional<Integer> getMaxId() {
    return channelMapper.getMaxId();
  }

  @Override
  public int update(Channel channel) {
    return channelMapper.update(channel);
  }

}
