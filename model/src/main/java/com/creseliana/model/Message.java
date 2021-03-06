package com.creseliana.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "messages")
public class Message extends Model implements Serializable {
    private static final long serialVersionUID = 4433583195054335866L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    private Chat chat;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User sender;
    @Column(name = "date")
    private LocalDateTime date;
    @Column(name = "message")
    private String message;
    @Column(name = "is_read")
    private boolean isRead;

    @Override
    public String toString() {
        return "Message{" +
                "id=" + getId() +
                ", chat=" + chat +
                ", sender=" + sender +
                ", date=" + date +
                ", message='" + message + '\'' +
                ", isRead=" + isRead +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message1 = (Message) o;
        return chat.equals(message1.chat) && sender.equals(message1.sender)
                && date.equals(message1.date) && message.equals(message1.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chat, sender, date, message);
    }
}
