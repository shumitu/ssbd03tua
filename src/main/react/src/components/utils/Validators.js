export const lettersOnly = /^[a-zA-ZĄąĆćĘęŁłŃńÓóŚśŹźŻż]+$/g;
export const mottoRegex = /^[^&+%~<>$£¥•|_€@=;'-]+$/g;

export function equalTo(ref, msg) {
  return this.test({
    name: 'equalTo',
    exclusive: false,
    // eslint-disable-next-line no-template-curly-in-string
    message: msg || '${path} must be the same as ${reference}',
    params: {
      reference: ref.path,
    },
    test(value) {
      return value === this.resolve(ref);
    },
  });
}

export function notEqualTo(ref, msg) {
  return this.test({
    name: 'notEqualTo',
    exclusive: false,
    // eslint-disable-next-line no-template-curly-in-string
    message: msg || '${path} must be the same as ${reference}',
    params: {
      reference: ref.path,
    },
    test(value) {
      return value !== this.resolve(ref);
    },
  });
}
