const venom = require('venom-bot');
const express = require('express');
const cors = require('cors');

const app = express();
app.use(express.json());
app.use(cors());

let client;

venom
    .create({
        session: 'remediar-session',
        useChrome: true,
        headless: true,
        browserPath: '/usr/bin/google-chrome',
    })
    .then((venomClient) => {
        client = venomClient;
        console.log('🟢 Venom Bot conectado.');
    })
    .catch((error) => {
        console.error('Erro ao iniciar Venom Bot', error);
    });

app.post('/enviar', async (req, res) => {
    const { numero, mensagem } = req.body;

    if (!numero || !mensagem) {
        return res.status(400).json({ erro: 'Número e mensagem são obrigatórios' });
    }

    try {
        const numeroFormatado = numero.replace(/\D/g, '');
        await client.sendText(`55${numeroFormatado}@c.us`, mensagem);
        res.json({ status: 'sucesso', numero, mensagem });
    } catch (error) {
        console.error(error);
        res.status(500).json({ erro: 'Falha ao enviar mensagem' });
    }
});

app.listen(3030, () => {
    console.log('🚀 Servidor rodando em http://localhost:3030');
});