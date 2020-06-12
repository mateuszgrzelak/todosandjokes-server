package pl.todosandjokes.api.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.todosandjokes.api.model.entity.Todo;
import pl.todosandjokes.api.model.entity.UserAccount;
import pl.todosandjokes.api.repository.UserAccountRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    UserAccountRepository userAccountRepository;

    TodoService(UserAccountRepository userAccountRepository){
        this.userAccountRepository = userAccountRepository;
    }

    public Todo getTodoByIndex(int index) {
        UserAccount userAccount = getUserAccount();
        List<Todo> todos = userAccount.getTodos();
        try{
            return todos.get(index);
        }catch(Exception e) {
            return null;
        }
    }

    public List<Todo> getTodos() {
        return getUserAccount().getTodos();
    }

    public boolean removeTodo(int index) {
        UserAccount userAccount = getUserAccount();
        try {
            userAccount.removeTodo(index);
        }catch(IndexOutOfBoundsException e){
            return false;
        }
        userAccountRepository.save(userAccount);
        return true;
    }

    public void addTodo(Todo todo){
        UserAccount userAccount = getUserAccount();
        userAccount.addTodo(todo);
        userAccountRepository.save(userAccount);
    }

    public Todo updateTodo(int index, Todo todo){
        UserAccount userAccount = getUserAccount();
        try{
            Todo updatedTodo = userAccount.updateTodo(index, todo);
            userAccountRepository.save(userAccount);
            return updatedTodo;
        }catch(Exception e){
            return null;
        }
    }

    public int size(){
        return getUserAccount().getTodos().size();
    }


    public UserAccount getUserAccount(){
        String name = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<UserAccount> userAccount = userAccountRepository.findByUsername(name);
        return userAccount.orElse(null);
    }

}
