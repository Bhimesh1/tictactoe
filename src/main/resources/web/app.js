const boardEl = document.getElementById('board');
const statusEl = document.getElementById('status');
const nameForm = document.getElementById('nameForm');
const xNameInput = document.getElementById('xName');
const oNameInput = document.getElementById('oName');
const newGameBtn = document.getElementById('newGame');

nameForm.addEventListener('submit', async (e) => {
  e.preventDefault();
  await startNewGame(xNameInput.value.trim() || 'Player X', oNameInput.value.trim() || 'Player O');
});

newGameBtn.addEventListener('click', async () => {
  await startNewGame(xNameInput.value.trim() || 'Player X', oNameInput.value.trim() || 'Player O');
});

boardEl.addEventListener('click', async (e) => {
  const cell = e.target.closest('.cell');
  if (!cell) return;
  const disabled = cell.classList.contains('disabled');
  if (disabled) return;

  const r = cell.getAttribute('data-r');
  const c = cell.getAttribute('data-c');
  await fetch(`/api/move?row=${r}&col=${c}`, { method: 'POST' });
  await refresh();
});

async function startNewGame(x, o) {
  xNameInput.value = x;
  oNameInput.value = o;
  await fetch(`/api/new?x=${encodeURIComponent(x)}&o=${encodeURIComponent(o)}`, { method: 'POST' });
  await refresh();
}

async function refresh() {
  const res = await fetch('/api/state');
  const state = await res.json();
  render(state);
}

function render(state) {
  const { board, status, currentName, currentSymbol, xName, oName, error } = state;

  if (error) {
    statusEl.textContent = `Error: ${error}`;
  } else {
    if (status === 'IN_PROGRESS') {
      statusEl.textContent = `${currentName} (${currentSymbol}) to move`;
    } else if (status === 'X_WINS') {
      statusEl.textContent = `${xName} (X) wins`;
    } else if (status === 'O_WINS') {
      statusEl.textContent = `${oName} (O) wins`;
    } else if (status === 'DRAW') {
      statusEl.textContent = `It's a draw`;
    } else {
      statusEl.textContent = '';
    }
  }

  // grid
  boardEl.innerHTML = '';
  for (let r = 0; r < 3; r++) {
    for (let c = 0; c < 3; c++) {
      const val = board[r][c] || '';
      const div = document.createElement('div');
      div.className = 'cell';
      div.setAttribute('data-r', r);
      div.setAttribute('data-c', c);
      div.textContent = val;
      if (status !== 'IN_PROGRESS' || val) {
        // disable finished games, and ignore clicking filled cells
        div.classList.add('disabled');
      }
      boardEl.appendChild(div);
    }
  }
}

// initial load
refresh();
