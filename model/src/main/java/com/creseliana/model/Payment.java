package com.creseliana.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payments")
public class Payment extends Model implements Serializable {
    @Serial
    private static final long serialVersionUID = 4675430274164100365L;

    @ManyToOne
    @JoinColumn(name = "ad_id")
    private Advertisement ad;
    @Column(name = "start_date")
    private LocalDateTime startDate;
    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + getId() +
                ", ad=" + ad +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment)) return false;
        if (!super.equals(o)) return false;
        Payment payment = (Payment) o;
        return ad.equals(payment.ad) && startDate.equals(payment.startDate)
                && endDate.equals(payment.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), ad, startDate, endDate);
    }
}
