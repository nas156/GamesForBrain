let DATA;

async function main() {
    DATA = await getStats('RepeatSequenceTest');
    DATA = DATA.sort(function (a, b) {return a-b});
    plot(DATA, 'plot', '');
}

window.onload = main;