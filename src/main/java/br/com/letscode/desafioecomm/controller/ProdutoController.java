package br.com.letscode.desafioecomm.controller;

import br.com.letscode.desafioecomm.service.ProdutoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RequestMapping("produtos")
@RestController
public class ProdutoController {
    private ProdutoService produtoService;

}
