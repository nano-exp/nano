const { css } = window.emotion
const { signal, effect } = window.preactSignalsCore

const AppClassName = css`
    #data {
        white-space: pre;
    }

    #btn-ack {
        margin-top: 1rem;
        font-size: 1rem;
        height: 32px;
        padding: 0 .5rem;
    }
`


async function main() {
    const appRef = document.querySelector('#app')
    appRef.className = AppClassName
    appRef.innerHTML = `
        <div>Ack Message</div>
        <div id="data"></div>
        <div>
            <button id="btn-ack">Ack Message</button>
        </div>
    `
    const ackButtonRef = document.querySelector('#btn-ack');
    const dataRef = document.querySelector('#data');
    const data = signal('')

    effect(() => {
        if (data.value) {
            dataRef.textContent = JSON.stringify(data.value, null, 2)
            if (data.value.ackTime) {
                ackButtonRef.setAttribute('disabled', 'disabled')
            }
        }
    })
    const updateMessageData = async (id) => {
        const response = await fetch(`/api/bark/message/${id}`)
        data.value = await response.json()
    }


    const searchParams = new URLSearchParams(location.search)
    const id = searchParams.get('id')
    if (!id) {
        location.href = '/'
    } else {
        await updateMessageData(id)
        ackButtonRef.addEventListener('click', async (ev) => {
            ackButtonRef.setAttribute('disabled', 'disabled')
            await fetch(`/api/bark/ack-message/${id}`, {
                method: 'POST',
            })
            ackButtonRef.removeAttribute('disabled')
            await updateMessageData(id)
        })
    }

}

main().catch((err) => console.error(err))