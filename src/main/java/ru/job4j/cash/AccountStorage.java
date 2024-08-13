package ru.job4j.cash;

import java.util.HashMap;
import java.util.Optional;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) == null;
    }

    public synchronized boolean update(Account account) {
        return accounts.replace(account.id(), account) != null;
    }

    public synchronized void delete(int id) {
        accounts.remove(id);
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        Optional<Account> fromAccOptional = getById(fromId);
        Optional<Account> toAccOptional = getById(toId);
        if (fromAccOptional.isPresent() && toAccOptional.isPresent()
            && fromAccOptional.get().id() != toAccOptional.get().id()
            && fromAccOptional.get().amount() >= amount) {
            Account fromUpdate = new Account(
                    fromAccOptional.get().id(), fromAccOptional.get().amount() - amount);
            Account toUpdate = new Account(
                    toAccOptional.get().id(), toAccOptional.get().amount() + amount);
            update(fromUpdate);
            update(toUpdate);
            return true;
        }
        return false;
    }
}