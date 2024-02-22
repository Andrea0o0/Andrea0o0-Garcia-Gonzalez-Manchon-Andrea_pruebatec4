package com.example.Models4.service.person;

import com.example.Models4.model.Client;
import com.example.Models4.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService implements IClientService {

    @Autowired
    private ClientRepository clientRepo;

    @Override
    public List<Client> getClients() {
        return clientRepo.findAll();
    }

    @Override
    public void saveClient(Client client) {
        clientRepo.save(client);
    }

    @Override
    public void deleteClient(Long id) {
        clientRepo.deleteById(id);
    }

    @Override
    public Client findClient(Long id) {
        return clientRepo.findById(id).orElse(null);
    }

    @Override
    public List<Client> findByEmail(String email) {
        return clientRepo.findByEmail(email);
    }
}
