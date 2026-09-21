package com.samuca.lanchehub.service;

import com.samuca.lanchehub.dto.MesaRequestDTO;
import com.samuca.lanchehub.dto.MesaResponseDTO;
import com.samuca.lanchehub.exception.RecursoNaoEncontrado;
import com.samuca.lanchehub.model.Mesa;
import com.samuca.lanchehub.model.StatusMesa;
import com.samuca.lanchehub.repository.MesaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MesaService {
    private final MesaRepository repository;
    public MesaService(MesaRepository repository) {
        this.repository = repository;
    }
    private MesaResponseDTO toResponse(Mesa mesa){
        return new MesaResponseDTO(
                mesa.getIdMesa(),
                mesa.getNumero(),
                mesa.getStatusMesa(),
                mesa.getQrToken()
                //getLanchonete.getIdLanchonete
        );
    }
    private Mesa toEntity(MesaRequestDTO dto){
        Mesa mesa = new Mesa();
        mesa.setNumero(dto.numeroMesa());
        mesa.setStatusMesa(StatusMesa.LIVRE);
        mesa.setQrToken(UUID.randomUUID().toString());
        //setLanchonete
        return mesa;
    }

    private Mesa pegarId(Long id){
        return repository.findById(id).orElseThrow(() -> new RecursoNaoEncontrado("Mesa não encontrada"));
    }

    public MesaResponseDTO buscarPorId(Long id) {
        return toResponse(pegarId(id));
    }

    public MesaResponseDTO criar(MesaRequestDTO dto){
        Mesa mesa = toEntity(dto);
        Mesa salva = repository.save(mesa);
        return toResponse(salva);
    }

    public MesaResponseDTO atualizar(Long id, MesaRequestDTO dto){
        Mesa mesa = pegarId(id);
        //lanchonete buscar por id
        mesa.setNumero(dto.numeroMesa());
        //mesa.setLanchonete(dto.lanchonete)
        return toResponse(repository.save(mesa));
    }

    public void deletar(Long id){
        Mesa mesa = pegarId(id);
        repository.delete(mesa);
    }

    public MesaResponseDTO alterarStatus(Long id, StatusMesa statusMesa){
        Mesa mesa = pegarId(id);
        mesa.setStatusMesa(statusMesa);
        Mesa mesaAtualizaado = repository.save(mesa);
        return toResponse(mesaAtualizaado);
    }
    public MesaResponseDTO acessarPorQrToken(String qrToken) {
        Mesa mesa = repository.findByQrToken(qrToken).orElseThrow(() ->
            new RecursoNaoEncontrado("Mesa não encontrada")
        );
        ocuparMesa(mesa);
        return toResponse(mesa);
    }
    public void ocuparMesa(Mesa mesa) {
        if (mesa.getStatusMesa() == StatusMesa.OCUPADA) {
            return;
        }
        mesa.setStatusMesa(StatusMesa.OCUPADA);
        repository.save(mesa);
    }
    public void liberarMesa(Mesa mesa) {
        mesa.setStatusMesa(StatusMesa.LIVRE);
        repository.save(mesa);
    }
}
