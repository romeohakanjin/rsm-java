-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Client :  127.0.0.1
-- Généré le :  Jeu 15 Février 2018 à 21:21
-- Version du serveur :  10.1.16-MariaDB
-- Version de PHP :  7.0.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `apocalypse`
--

-- --------------------------------------------------------

--
-- Structure de la table `activite_externe`
--

CREATE TABLE `activite_externe` (
  `id_activite` int(10) NOT NULL,
  `titre` varchar(50) NOT NULL,
  `description` varchar(1000) NOT NULL,
  `ville` varchar(20) NOT NULL,
  `id_type_activite` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `annonce`
--

CREATE TABLE `annonce` (
  `id_annonce` int(10) NOT NULL,
  `id_utilisateur` int(10) NOT NULL,
  `titre` varchar(50) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `capacite_max` int(10) DEFAULT NULL,
  `date_creation` datetime DEFAULT NULL,
  `actif` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `annonce`
--

INSERT INTO `annonce` (`id_annonce`, `id_utilisateur`, `titre`, `description`, `capacite_max`, `date_creation`, `actif`) VALUES
(1, 1, 'test1', 'testDescription1', 666, '2018-01-01 00:00:00', 1),
(15, 2, 'dd1', '515									', 5515, '2018-02-11 18:06:21', 1),
(20, 2, 'raspouti', 'grasti									', 6024516, '2018-02-13 06:57:51', 1);

-- --------------------------------------------------------

--
-- Structure de la table `commentaire`
--

CREATE TABLE `commentaire` (
  `id_commentaire` int(11) NOT NULL,
  `id_reservation` int(11) NOT NULL,
  `id_utilisateur` int(11) NOT NULL,
  `commentaire` varchar(1000) NOT NULL,
  `signaler` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `commentaire`
--

INSERT INTO `commentaire` (`id_commentaire`, `id_reservation`, `id_utilisateur`, `commentaire`, `signaler`) VALUES
(1, 1, 24, 'Gonnier', 0),
(2, 2, 2, 'razk', 0),
(3, 3, 2, 'pourrish', 1),
(4, 3, 3, 'kokonut', 0);

-- --------------------------------------------------------

--
-- Structure de la table `etat_reservation`
--

CREATE TABLE `etat_reservation` (
  `id_etat` int(10) NOT NULL,
  `libelle` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `etat_reservation`
--

INSERT INTO `etat_reservation` (`id_etat`, `libelle`) VALUES
(1, 'En attente de confirmation'),
(2, 'Hotelier a confirme'),
(3, 'En attente de validation'),
(4, 'Validee');

-- --------------------------------------------------------

--
-- Structure de la table `hotel`
--

CREATE TABLE `hotel` (
  `id_hotel` int(11) NOT NULL,
  `nom` varchar(35) NOT NULL,
  `adresse` varchar(35) NOT NULL,
  `code_postal` varchar(15) NOT NULL,
  `ville` varchar(60) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `hotel`
--

INSERT INTO `hotel` (`id_hotel`, `nom`, `adresse`, `code_postal`, `ville`) VALUES
(1, 'test1', '556 villa 5', '75013', 'Paris'),
(2, 'Hotel 1', '100 rue des brebis', '75015', 'Paris'),
(7, 'vatec', '', '', ''),
(8, 'popo', '', '', ''),
(9, 'lm', 'lm', 'lm', 'lm'),
(10, 'ko', 'ko', 'ko', 'ko');

-- --------------------------------------------------------

--
-- Structure de la table `paiement`
--

CREATE TABLE `paiement` (
  `id_paiement` int(11) NOT NULL,
  `id_reservation` int(11) NOT NULL,
  `date_paiement` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `reservation`
--

CREATE TABLE `reservation` (
  `id_reservation` int(10) NOT NULL,
  `id_annonce` int(10) NOT NULL,
  `id_utilisateur` int(10) NOT NULL,
  `prix` double NOT NULL,
  `capacite_max` int(10) NOT NULL,
  `date_sejour` datetime NOT NULL,
  `id_statut_reservation` int(10) NOT NULL,
  `id_etat_reservation` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `reservation`
--

INSERT INTO `reservation` (`id_reservation`, `id_annonce`, `id_utilisateur`, `prix`, `capacite_max`, `date_sejour`, `id_statut_reservation`, `id_etat_reservation`) VALUES
(1, 1, 1, 5654, 3, '2018-02-08 00:00:00', 2, 2),
(2, 15, 2, 12644, 789, '2018-02-12 00:00:00', 1, 3),
(3, 15, 2, 4596, 14, '2018-02-09 00:00:00', 3, 1);

-- --------------------------------------------------------

--
-- Structure de la table `statut_reservation`
--

CREATE TABLE `statut_reservation` (
  `id_statut_reservation` int(10) NOT NULL,
  `libelle` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `statut_reservation`
--

INSERT INTO `statut_reservation` (`id_statut_reservation`, `libelle`) VALUES
(1, 'Terminee'),
(2, 'En cours'),
(3, 'A venir');

-- --------------------------------------------------------

--
-- Structure de la table `type_activite`
--

CREATE TABLE `type_activite` (
  `id_type_activite` int(10) NOT NULL,
  `libelle` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `type_utilisateur`
--

CREATE TABLE `type_utilisateur` (
  `id_type_utilisateur` int(10) NOT NULL,
  `libelle` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `type_utilisateur`
--

INSERT INTO `type_utilisateur` (`id_type_utilisateur`, `libelle`) VALUES
(1, 'Administrateur'),
(2, 'Hotelier'),
(3, 'Standard');

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `id_utilisateur` int(10) NOT NULL,
  `id_type_utilisateur` int(10) DEFAULT '3',
  `id_hotel` int(50) DEFAULT NULL,
  `nom` varchar(50) DEFAULT NULL,
  `prenom` varchar(50) DEFAULT NULL,
  `mail` varchar(70) DEFAULT NULL,
  `motDePasse` varchar(25) NOT NULL,
  `mobile` varchar(15) DEFAULT NULL,
  `adresse` varchar(50) DEFAULT NULL,
  `code_postal` varchar(10) DEFAULT NULL,
  `ville` varchar(50) DEFAULT NULL,
  `point_bonus` int(100) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `utilisateur`
--

INSERT INTO `utilisateur` (`id_utilisateur`, `id_type_utilisateur`, `id_hotel`, `nom`, `prenom`, `mail`, `motDePasse`, `mobile`, `adresse`, `code_postal`, `ville`, `point_bonus`) VALUES
(1, 1, NULL, 'LIM', 'Sindy', 'sindy.lim91@gmail.com', 'test01', '0635267495', '100 rue des tests', '75015', 'Paris', 0),
(2, 2, NULL, 'HAKANJIN', 'Romeo', 'hakanjin.romeo96@gmail.com', 'test02', '0635987465', '12 allee des test', '95800', 'CERGY', 0),
(3, 3, NULL, 'DIAGNE', 'Massamba', 'massdinho10@gmail.com', 'test03', '0745873695', '3 avenue de test', '93600', 'AUNAY-SOUS-BOIS', 0),
(24, 2, NULL, 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 0),
(25, 2, NULL, 'lp', 'lp', 'lp', 'lp', 'pl', 'pl', 'pl', 'lp', 0),
(26, 2, 1, 'lplpl', 'plplpl', 'lpllp', 'plplp', 'plplp', 'lplpl', 'plplpl', 'plplpl', 0);

--
-- Index pour les tables exportées
--

--
-- Index pour la table `activite_externe`
--
ALTER TABLE `activite_externe`
  ADD PRIMARY KEY (`id_activite`),
  ADD KEY `id_type` (`id_type_activite`);

--
-- Index pour la table `annonce`
--
ALTER TABLE `annonce`
  ADD PRIMARY KEY (`id_annonce`),
  ADD KEY `FK1_ANNONCE` (`id_utilisateur`);

--
-- Index pour la table `commentaire`
--
ALTER TABLE `commentaire`
  ADD PRIMARY KEY (`id_commentaire`),
  ADD KEY `FK2_COMMENTAIRE` (`id_utilisateur`),
  ADD KEY `id_reservation` (`id_reservation`);

--
-- Index pour la table `etat_reservation`
--
ALTER TABLE `etat_reservation`
  ADD PRIMARY KEY (`id_etat`);

--
-- Index pour la table `hotel`
--
ALTER TABLE `hotel`
  ADD PRIMARY KEY (`id_hotel`);

--
-- Index pour la table `paiement`
--
ALTER TABLE `paiement`
  ADD PRIMARY KEY (`id_paiement`),
  ADD KEY `id_reservation` (`id_reservation`);

--
-- Index pour la table `reservation`
--
ALTER TABLE `reservation`
  ADD PRIMARY KEY (`id_reservation`),
  ADD KEY `FK1_RESERVATION` (`id_annonce`),
  ADD KEY `FK2_RESERVATION` (`id_utilisateur`),
  ADD KEY `FK3_RESERVATION` (`id_etat_reservation`),
  ADD KEY `FK4_RESERVATION` (`id_statut_reservation`);

--
-- Index pour la table `statut_reservation`
--
ALTER TABLE `statut_reservation`
  ADD PRIMARY KEY (`id_statut_reservation`);

--
-- Index pour la table `type_activite`
--
ALTER TABLE `type_activite`
  ADD PRIMARY KEY (`id_type_activite`);

--
-- Index pour la table `type_utilisateur`
--
ALTER TABLE `type_utilisateur`
  ADD PRIMARY KEY (`id_type_utilisateur`);

--
-- Index pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`id_utilisateur`),
  ADD KEY `FK1_UTILISATEUR` (`id_type_utilisateur`),
  ADD KEY `FK2_UTILISATEUR` (`id_hotel`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `activite_externe`
--
ALTER TABLE `activite_externe`
  MODIFY `id_activite` int(10) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `annonce`
--
ALTER TABLE `annonce`
  MODIFY `id_annonce` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;
--
-- AUTO_INCREMENT pour la table `commentaire`
--
ALTER TABLE `commentaire`
  MODIFY `id_commentaire` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT pour la table `etat_reservation`
--
ALTER TABLE `etat_reservation`
  MODIFY `id_etat` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT pour la table `hotel`
--
ALTER TABLE `hotel`
  MODIFY `id_hotel` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT pour la table `paiement`
--
ALTER TABLE `paiement`
  MODIFY `id_paiement` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `reservation`
--
ALTER TABLE `reservation`
  MODIFY `id_reservation` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT pour la table `statut_reservation`
--
ALTER TABLE `statut_reservation`
  MODIFY `id_statut_reservation` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT pour la table `type_activite`
--
ALTER TABLE `type_activite`
  MODIFY `id_type_activite` int(10) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `type_utilisateur`
--
ALTER TABLE `type_utilisateur`
  MODIFY `id_type_utilisateur` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  MODIFY `id_utilisateur` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;
--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `activite_externe`
--
ALTER TABLE `activite_externe`
  ADD CONSTRAINT `activite_externe_ibfk_1` FOREIGN KEY (`id_type_activite`) REFERENCES `type_activite` (`id_type_activite`);

--
-- Contraintes pour la table `annonce`
--
ALTER TABLE `annonce`
  ADD CONSTRAINT `FK1_ANNONCE` FOREIGN KEY (`id_utilisateur`) REFERENCES `utilisateur` (`id_utilisateur`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `commentaire`
--
ALTER TABLE `commentaire`
  ADD CONSTRAINT `FK2_COMMENTAIRE` FOREIGN KEY (`id_utilisateur`) REFERENCES `utilisateur` (`id_utilisateur`),
  ADD CONSTRAINT `commentaire_ibfk_1` FOREIGN KEY (`id_reservation`) REFERENCES `reservation` (`id_reservation`);

--
-- Contraintes pour la table `paiement`
--
ALTER TABLE `paiement`
  ADD CONSTRAINT `paiement_ibfk_1` FOREIGN KEY (`id_reservation`) REFERENCES `reservation` (`id_reservation`);

--
-- Contraintes pour la table `reservation`
--
ALTER TABLE `reservation`
  ADD CONSTRAINT `FK1_RESERVATION` FOREIGN KEY (`id_annonce`) REFERENCES `annonce` (`id_annonce`),
  ADD CONSTRAINT `FK2_RESERVATION` FOREIGN KEY (`id_utilisateur`) REFERENCES `utilisateur` (`id_utilisateur`),
  ADD CONSTRAINT `FK3_RESERVATION` FOREIGN KEY (`id_etat_reservation`) REFERENCES `etat_reservation` (`id_etat`),
  ADD CONSTRAINT `FK4_RESERVATION` FOREIGN KEY (`id_statut_reservation`) REFERENCES `statut_reservation` (`id_statut_reservation`);

--
-- Contraintes pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD CONSTRAINT `FK1_UTILISATEUR` FOREIGN KEY (`id_type_utilisateur`) REFERENCES `type_utilisateur` (`id_type_utilisateur`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `utilisateur_ibfk_1` FOREIGN KEY (`id_hotel`) REFERENCES `hotel` (`id_hotel`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
