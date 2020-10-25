package org.example.third.tasks.part.two.repository;

import org.example.third.tasks.part.two.model.User;
import org.example.third.tasks.part.two.model.UserSubscription;
import org.example.third.tasks.part.two.model.UserSubscriptionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, UserSubscriptionId> {
    List<UserSubscription> findBySubscriber(User user);
}
