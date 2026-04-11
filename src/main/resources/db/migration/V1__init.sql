CREATE TABLE users
(
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    email      VARCHAR(100) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    name       VARCHAR(50)  NOT NULL,
    role       VARCHAR(20)  NOT NULL,
    created_at DATETIME(6)  NOT NULL,
    updated_at DATETIME(6)  NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_users_email UNIQUE (email)
);

CREATE TABLE clubs
(
    id          BIGINT      NOT NULL AUTO_INCREMENT,
    user_id     BIGINT      NOT NULL,
    name        VARCHAR(100) NOT NULL,
    description TEXT,
    status      VARCHAR(20) NOT NULL,
    created_at  DATETIME(6) NOT NULL,
    updated_at  DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_clubs_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE venues
(
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    name       VARCHAR(100) NOT NULL,
    address    VARCHAR(255),
    created_at DATETIME(6)  NOT NULL,
    updated_at DATETIME(6)  NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE events
(
    id                   BIGINT       NOT NULL AUTO_INCREMENT,
    club_id              BIGINT,
    venue_id             BIGINT,
    name                 VARCHAR(200) NOT NULL,
    description          TEXT,
    event_at             DATETIME(6)  NOT NULL,
    booking_open_at      DATETIME(6)  NOT NULL,
    booking_close_at     DATETIME(6)  NOT NULL,
    cancel_deadline_days INT          NOT NULL DEFAULT 3,
    max_seats_per_user   INT          NOT NULL DEFAULT 4,
    created_at           DATETIME(6)  NOT NULL,
    updated_at           DATETIME(6)  NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_events_club FOREIGN KEY (club_id) REFERENCES clubs (id),
    CONSTRAINT fk_events_venue FOREIGN KEY (venue_id) REFERENCES venues (id)
);

CREATE TABLE seats
(
    id         BIGINT      NOT NULL AUTO_INCREMENT,
    venue_id   BIGINT,
    section    VARCHAR(20) NOT NULL,
    row_num    INT         NOT NULL,
    number     INT         NOT NULL,
    seat_type  VARCHAR(20) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_seat_venue_section_row_number UNIQUE (venue_id, section, row_num, number),
    CONSTRAINT fk_seats_venue FOREIGN KEY (venue_id) REFERENCES venues (id)
);

CREATE TABLE event_seats
(
    id         BIGINT      NOT NULL AUTO_INCREMENT,
    event_id   BIGINT,
    seat_id    BIGINT,
    price      INT         NOT NULL,
    status     VARCHAR(20) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_event_seat_event_id_seat_id UNIQUE (event_id, seat_id),
    CONSTRAINT fk_event_seats_event FOREIGN KEY (event_id) REFERENCES events (id),
    CONSTRAINT fk_event_seats_seat FOREIGN KEY (seat_id) REFERENCES seats (id)
);

CREATE TABLE reservations
(
    id          BIGINT      NOT NULL AUTO_INCREMENT,
    user_id     BIGINT,
    event_id    BIGINT,
    status      VARCHAR(20) NOT NULL,
    reserved_at DATETIME(6) NOT NULL,
    canceled_at DATETIME(6),
    created_at  DATETIME(6) NOT NULL,
    updated_at  DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_reservations_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_reservations_event FOREIGN KEY (event_id) REFERENCES events (id)
);

CREATE TABLE reservation_seats
(
    id            BIGINT      NOT NULL AUTO_INCREMENT,
    reservation_id BIGINT,
    event_seat_id  BIGINT,
    created_at    DATETIME(6) NOT NULL,
    updated_at    DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_reservation_seat_event_seat_id UNIQUE (event_seat_id),
    CONSTRAINT fk_reservation_seats_reservation FOREIGN KEY (reservation_id) REFERENCES reservations (id),
    CONSTRAINT fk_reservation_seats_event_seat FOREIGN KEY (event_seat_id) REFERENCES event_seats (id)
);

CREATE TABLE payments
(
    id             BIGINT       NOT NULL AUTO_INCREMENT,
    reservation_id BIGINT,
    order_id       VARCHAR(100) NOT NULL,
    payment_key    VARCHAR(200),
    mid            VARCHAR(50),
    method         VARCHAR(50),
    total_amount   INT          NOT NULL,
    status         VARCHAR(20)  NOT NULL,
    requested_at   DATETIME(6)  NOT NULL,
    approved_at    DATETIME(6),
    created_at     DATETIME(6)  NOT NULL,
    updated_at     DATETIME(6)  NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_payments_reservation_id UNIQUE (reservation_id),
    CONSTRAINT uk_payments_order_id UNIQUE (order_id),
    CONSTRAINT fk_payments_reservation FOREIGN KEY (reservation_id) REFERENCES reservations (id)
);

CREATE TABLE refunds
(
    id            BIGINT      NOT NULL AUTO_INCREMENT,
    payment_id    BIGINT,
    refund_amount INT         NOT NULL,
    reason        VARCHAR(255),
    status        VARCHAR(20) NOT NULL,
    refunded_at   DATETIME(6),
    created_at    DATETIME(6) NOT NULL,
    updated_at    DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_refunds_payment_id UNIQUE (payment_id),
    CONSTRAINT fk_refunds_payment FOREIGN KEY (payment_id) REFERENCES payments (id)
);